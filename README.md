# Filepicker For Android

This repo is home to a very simple file picker project for Android devices. It allows device
navigation and picking of files that are then sent back to calling activity as abstractions containing
basic information such as path, name ect.

![filepicker](https://cloud.githubusercontent.com/assets/8562956/19419726/a4e17a32-93ab-11e6-8738-1ccd8ed77f4d.png)
![filepickerlist](https://cloud.githubusercontent.com/assets/8562956/19419727/a9e1f78c-93ab-11e6-8c36-6653348a7244.png)

## Use

### Import

To use this picker in you project you need to:

* Import this repo alongside you project as a module (File -> New -> Import Module)
* Include the module is settings.gradle: include ":app", ":\<module name\>"
* In your build.gradle add dependency like so: compile project(":\<module name>\")

### Launch Activity

Use Filepicker class in you intent for result. Use FilepickerConfig to provide basic settings. In response
you will get a list of FilepickerFile objects.

## Licence

You may use this project as you see fit. There is no warranty or guarantee whatsoever.

