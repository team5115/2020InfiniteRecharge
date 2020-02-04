//
// Inputs
//
Inputs
{
	Mat source0;
}

//
// Variables
//
Outputs
{
	Mat blurOutput;
	Mat hsvThresholdOutput;
	BlobsReport findBlobsOutput;
}

//
// Steps
//

Step Blur0
{
    Mat blurInput = source0;
    BlurType blurType = BOX;
    Double blurRadius = 1.8018018018018014;

    blur(blurInput, blurType, blurRadius, blurOutput);
}

Step HSV_Threshold0
{
    Mat hsvThresholdInput = blurOutput;
    List hsvThresholdHue = [6.474820143884892, 37.16723549488056];
    List hsvThresholdSaturation = [190.33273381294958, 255.0];
    List hsvThresholdValue = [139.8830935251799, 255.0];

    hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);
}

Step Find_Blobs0
{
    Mat findBlobsInput = hsvThresholdOutput;
    Double findBlobsMinArea = 0.0;
    List findBlobsCircularity = [0.6474820143884892, 1.0];
    Boolean findBlobsDarkBlobs = false;

    findBlobs(findBlobsInput, findBlobsMinArea, findBlobsCircularity, findBlobsDarkBlobs, findBlobsOutput);
}




