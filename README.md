# ImageEditor2.0
Furthering my other image editor, this code uses a 2D doubly-linked list to map the pixels in an image and edit them to change the image. 
To use and test the image editor just create a main method class to access the image methods and constructor. There are a bunch of methods included in the program:
- insert row method with signature insertRow(int index, T value) inserts a row anywhere in the image all with T value.
- remove Column method with signature removeColumn(int index) which removes any column in the image.
- compress method with signature compress() that removes any adjacent rows or columns that are identical.
- add Border method with signature addBorder() that adds a border around the entire image that mimics the inner image values.
- removeBorder method with signature removeBorder() that removes the all the outside pixels.
- maxFilter method with signature maxFilter() that finds the largest pixel value in a 3x3 neighborhood and sets every node in the neighborhood to that value.
