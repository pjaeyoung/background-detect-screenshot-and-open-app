import { NativeModules } from 'react-native';

type BackgroundDetectScreenshotAndOpenAppType = {
  multiply(a: number, b: number): Promise<number>;
};

const { BackgroundDetectScreenshotAndOpenApp } = NativeModules;

export default BackgroundDetectScreenshotAndOpenApp as BackgroundDetectScreenshotAndOpenAppType;
