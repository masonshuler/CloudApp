/*
 * Created by Jordan Kuhn on 2018.02.15 * 
 * Copyright © 2018 Jordan Kuhn. All rights reserved. * 
 */
package com.mycompany.controllers;

import com.mycompany.EntityBeans.User;
import com.mycompany.EntityBeans.UserPhoto;
import com.mycompany.managers.Constants;
import com.mycompany.FacadeBeans.UserFacade;
import com.mycompany.FacadeBeans.UserPhotoFacade;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import org.imgscalr.Scalr;
import org.primefaces.event.CaptureEvent;

/**
 *
 * @author Jordan
 */
@Named("photoCameraController")
@RequestScoped

public class PhotoCameraController implements Serializable {

    private String filename;
    FacesMessage resultMsg;

    /*
    The instance variable 'userFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference
    of the UserFacade object, after it is instantiated at runtime, into the instance variable 'userFacade'.
     */
    @EJB
    private UserFacade userFacade;

    /*
    The instance variable 'userPhotoFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference 
    of the UserPhotoFacade object, after it is instantiated at runtime, into the instance variable 'userPhotoFacade'.
     */
    @EJB
    private UserPhotoFacade userPhotoFacade;

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FacesMessage getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(FacesMessage resultMsg) {
        this.resultMsg = resultMsg;
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public UserPhotoFacade getUserPhotoFacade() {
        return userPhotoFacade;
    }

    public void setUserPhotoFacade(UserPhotoFacade userPhotoFacade) {
        this.userPhotoFacade = userPhotoFacade;
    }

    /*
    ================
    Instance Methods
    ================
     */
    public void onCapture(CaptureEvent captureEvent) {

        // This sets the necessary flag to ensure the messages are preserved.
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        // This instance variable is accessed in ChangePhoto.xhtml
        filename = "tmp_file";

        // Obtain the captured photo image data as a stream of bytes
        byte[] capturedPhotoImageData = captureEvent.getData();

        // Delete signed-in user's uploaded photo file, its thumbnail file, tmp_file, and its database record.
        deletePhoto();

        /*
         We will store the user's captured photo image in the tmp_file under the external
         directory CloudStorage/PhotoStorage/. We do not assign a file extension to tmp_file 
         because we want to be able to reuse the same file for many images with different 
         file extensions as a temporary file before creating the actual ones.
         */
        String absolutePathToTempFile = Constants.PHOTOS_ABSOLUTE_PATH + filename;

        // Create a new File object for tmp_file using its absolute filepath
        File capturedPhotoTempFile = new File(absolutePathToTempFile);

        // The class FileImageOutputStream enables writing its output directly to a File 
        FileImageOutputStream imageOutput;
        try {
            // Instantiate a new FileImageOutputStream object for the tmp_file
            imageOutput = new FileImageOutputStream(capturedPhotoTempFile);

            // Write the capturedPhotoImageData byte stream into the tmp_file
            imageOutput.write(capturedPhotoImageData, 0, capturedPhotoImageData.length);

            // Close the tmp_file
            imageOutput.close();

        } catch (IOException e) {
            resultMsg = new FacesMessage("Error in writing captured photo image! See: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, resultMsg);
        }

        // Obtain the username of the logged-in user
        String user_name = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("username");

        // Obtain the object reference of the logged-in User object
        User user = getUserFacade().findByUsername(user_name);

        /*
         Obtain the list of Photo objects that belong to the User whose
         database primary key is user.getId()
         */
        List<UserPhoto> photoList = getUserPhotoFacade().findPhotosByUserID(user.getId());

        if (!photoList.isEmpty()) {
            // Remove the photo from the database
            getUserPhotoFacade().remove(photoList.get(0));
        }

        // Construct a new Photo object with PNG file extension and user's object reference
        UserPhoto newPhoto = new UserPhoto("png", user);

        // Create a record for the new Photo object in the database
        getUserPhotoFacade().create(newPhoto);

        // Obtain the object reference of the first and only Photo object of the
        // user whose primary key is user.getId()
        UserPhoto photo = getUserPhotoFacade().findPhotosByUserID(user.getId()).get(0);

        try {
            // Create a new File object for tmp_file using its absolute filepath
            File photoTempFile = new File(absolutePathToTempFile);

            // Convert the captured photo stored in the temp_file into an InputStream
            InputStream targetStream = new FileInputStream(photoTempFile);

            // Write the captured photo's input stream of bytes under the photo object's
            // filename using the inputStreamToFile method given below
            File capturedPhotoFile = inputStreamToFile(targetStream, photo.getPhotoFilename());

            // Create and save the thumbnail version of the captured photo file
            saveThumbnail(capturedPhotoFile, photo);

        } catch (IOException e) {
            resultMsg = new FacesMessage("Error in Converting Temp File into Input Stream! See: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, resultMsg);
        }
    }

    /*
    =======================================================================
    Write a given capturedPhotoImageData stream into a file with given name
    =======================================================================
     */
    /**
     * @param inputStream of bytes to be written into file with name targetFilename
     * @param targetFilename
     * @return the created file targetFile
     * @throws IOException
     */
    private File inputStreamToFile(InputStream inputStream, String targetFilename) throws IOException {

        // This sets the necessary flag to ensure the messages are preserved.
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        File targetFile = null;

        try {
            /*
            inputStream.available() returns an estimate of the number of bytes that can be read from
            the inputStream without blocking by the next invocation of a method for this input stream.
            A memory buffer of bytes is created with the size of estimated number of bytes.
             */
            byte[] buffer = new byte[inputStream.available()];

            // Read the bytes of capturedPhotoImageData from the inputStream into the created memory buffer. 
            inputStream.read(buffer);

            // Create a new empty file with the given name targetFilename in CloudStorage/PhotoStorage
            targetFile = new File(Constants.PHOTOS_ABSOLUTE_PATH, targetFilename);

            // A file OutputStream is an output stream for writing capturedPhotoImageData to a file
            OutputStream outStream;

            /*
            FileOutputStream is intended for writing streams of raw bytes such as image capturedPhotoImageData.
            Create a new FileOutputStream for writing to the empty targetFile
             */
            outStream = new FileOutputStream(targetFile);

            // Create the targetFile in CloudStorage/PhotoStorage with the inputStream given
            outStream.write(buffer);

            // Close the output stream and release any system resources associated with it. 
            outStream.close();

        } catch (IOException e) {
            resultMsg = new FacesMessage("Something went wrong in input stream to file! See: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, resultMsg);
        }

        // Return the created targetFile
        return targetFile;
    }

    /*
    ============================================
    Store Signed-In User's Thumbnail Photo Image
    ============================================

    When user uploads a photo, a thumbnail (small) version of the photo image
    is created in this method by using the Scalr.resize method provided in the
    imgscalr (Java Image Scaling Library) imported as imgscalr-lib-4.2.jar
     */
    private void saveThumbnail(File inputFile, UserPhoto inputPhoto) {

        // This sets the necessary flag to ensure the messages are preserved.
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        try {
            // Buffer the photo image from the uploaded inputFile
            BufferedImage uploadedPhoto = ImageIO.read(inputFile);

            /*
            The thumbnail photo image size is set to 200x200px in Constants.java as follows:
            public static final Integer THUMBNAIL_SIZE = 200;

            If the user uploads a large photo file, we will scale it down to THUMBNAIL_SIZE
            and use it so that the size is reasonable for performance reasons.

            The photo image scaling is properly done by using the imgscalr-lib-4.2.jar file.

            The thumbnail file is named as "userId_thumbnail.fileExtension", 
            e.g., 5_thumbnail.jpeg for user with id 5.
             */
            // Scale the uploaded photo image to the THUMBNAIL_SIZE using imgscalr.
            BufferedImage thumbnailPhoto = Scalr.resize(uploadedPhoto, 200);

            // Create the thumbnail photo file in CloudStorage/PhotoStorage
            File thumbnailPhotoFile = new File(Constants.PHOTOS_ABSOLUTE_PATH, inputPhoto.getThumbnailFileName());

            // Write the thumbnailPhoto into thumbnailPhotoFile with the file extension
            ImageIO.write(thumbnailPhoto, inputPhoto.getExtension(), thumbnailPhotoFile);

        } catch (IOException e) {
            resultMsg = new FacesMessage("Something went wrong while saving the thumbnail file! See: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, resultMsg);
        }
    }

    /*
    =============================
    Delete Signed-In User's Photo
    =============================
     */
    public void deletePhoto() {

        // Obtain the signed-in user's username
        String usernameOfSignedInUser = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("username");

        // Obtain the object reference of the signed-in user
        User signedInUser = getUserFacade().findByUsername(usernameOfSignedInUser);

        // Obtain the id (primary key in the database) of the signedInUser object
        Integer userId = signedInUser.getId();

        /*
        Obtain the list of Photo file objects that belong to the signed-in user whose
        database primary key is userId. The list will contain only one photo or nothing.
         */
        List<UserPhoto> photoList = getUserPhotoFacade().findPhotosByUserID(userId);

        if (!photoList.isEmpty()) {

            // Obtain the object reference of the first Photo object in the list
            UserPhoto photo = photoList.get(0);

            try {
                // Delete the photo file from CloudStorage/PhotoStorage
                Files.deleteIfExists(Paths.get(photo.getPhotoFilePath()));

                // Delete the thumbnail file from CloudStorage/PhotoStorage
                Files.deleteIfExists(Paths.get(photo.getThumbnailFilePath()));

                // Delete the temporary file from CloudStorage/PhotoStorage
                Files.deleteIfExists(Paths.get(photo.getTemporaryFilePath()));

                // Delete the photo file record from the UsersVideosDB database
                getUserPhotoFacade().remove(photo);
                // UserPhotoFacade inherits the remove() method from AbstractFacade

            } catch (IOException e) {
                resultMsg = new FacesMessage("Something went wrong while deleting the photo file! See: " + e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, resultMsg);
            }
        }

    }

    /*
    =============================
    Remove Captured Photo to Redo
    =============================
     */
    public String redo() {
        filename = "";
        return "ChangePhoto?faces-redirect=true";
    }
}