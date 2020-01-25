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
	Mat hsvThresholdOutput;
	Mat maskOutput;
	Mat blurOutput;
	BlobsReport findBlobsOutput;
}

//
// Steps
//

Step HSV_Threshold0
{
    Mat hsvThresholdInput = source0;
    List hsvThresholdHue = [12.949640287769784, 44.84641638225257];
    List hsvThresholdSaturation = [139.1726618705036, 255.0];
    List hsvThresholdValue = [133.00359712230215, 255.0];

    hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);
}

Step Mask0
{
    Mat maskInput = source0;
    Mat maskMask = hsvThresholdOutput;

    mask(maskInput, maskMask, maskOutput);
}

Step Blur0
{
    Mat blurInput = maskOutput;
    BlurType blurType = BOX;
    Double blurRadius = 5.405405405405405;

    blur(blurInput, blurType, blurRadius, blurOutput);
}

Step Find_Blobs0
{
    Mat findBlobsInput = blurOutput;
    Double findBlobsMinArea = 1;
    List findBlobsCircularity = [0.0, 1.0];
    Boolean findBlobsDarkBlobs = false;

    findBlobs(findBlobsInput, findBlobsMinArea, findBlobsCircularity, findBlobsDarkBlobs, findBlobsOutput);
}




