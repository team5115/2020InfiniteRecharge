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
    Double blurRadius = 20.720720720720728;

    blur(blurInput, blurType, blurRadius, blurOutput);
}

Step HSV_Threshold0
{
    Mat hsvThresholdInput = blurOutput;
    List hsvThresholdHue = [22.66187050359712, 41.77474402730375];
    List hsvThresholdSaturation = [43.57014388489208, 187.5511945392491];
    List hsvThresholdValue = [59.62230215827338, 255.0];

    hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);
}

Step Find_Blobs0
{
    Mat findBlobsInput = hsvThresholdOutput;
    Double findBlobsMinArea = 50.0;
    List findBlobsCircularity = [0.0, 1.0];
    Boolean findBlobsDarkBlobs = false;

    findBlobs(findBlobsInput, findBlobsMinArea, findBlobsCircularity, findBlobsDarkBlobs, findBlobsOutput);
}




