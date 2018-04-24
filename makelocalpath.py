import os
cwd = os.getcwd()
with open('./LocalGoodWillOnline/web/WEB-INF/glassfish-web.xml', 'r') as input_file, open('tmp', 'w') as output_file:
    for line in input_file:
        if "alternatedocroot_1" in line:
            newline = "  <property name=\"alternatedocroot_1\" " \
                      "value=\"from=/CloudApp/* dir=" + cwd + "\" />\n"
            output_file.write(newline)
        else:
            output_file.write(line)
with open('./LocalGoodWillOnline/web/WEB-INF/glassfish-web.xml', 'w') as output_file_file, open('tmp', 'r') as input_file:
    for line in input_file:
       output_file.write(line)
print "glassfish-web changed to " + cwd
os.remove("tmp")
print "tmp files removed"