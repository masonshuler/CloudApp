import os
from shutil import move
cwd = os.getcwd()
pwd = ""
i = 0
for l in cwd:
    if i > len(cwd) - 10:
        break
    i = i + 1

    if l == '\\' :
        pwd = pwd + '/'
    else:
        pwd = pwd + l

with open('./makeLocalPathFiles/glassfish-web.xml', 'r') as input_file, open('tmp', 'w') as output_file:
    for line in input_file:
        if "alternatedocroot_1" in line:
            newline = "  <property name=\"alternatedocroot_1\" " \
                      "value=\"from=/CloudApp/* dir=" + pwd + "\" />\n"
            output_file.write(newline)
        else:
            output_file.write(line)
print "glassfish-web changed to " + pwd
move('tmp', './LocalGoodWillOnline/web/WEB-INF/glassfish-web.xml')


with open('./makeLocalPathFiles/Constants.java', 'r') as input_file, open('tmp', 'w') as output_file:
    for line in input_file:
        if "ITEMS_ABSOLUTE_PATH" in line :
            newline = "    public static final String ITEMS_ABSOLUTE_PATH = \"" + pwd + "/CloudApp/ItemPhotoStorage/\";\n"
            output_file.write(newline)
        elif "PHOTOS_ABSOLUTE_PATH" in line :
                newline = "    public static final String PHOTOS_ABSOLUTE_PATH = \"" + pwd + "/CloudApp/UserPhotoStorage/\";\n"
                output_file.write(newline)
        else :
            output_file.write(line)
print "constants.java changed to " + pwd
move('tmp', './LocalGoodwillOnline/src/java/com/mycompany/managers/Constants.java')
# os.remove("tmp")
print "tmp files removed"