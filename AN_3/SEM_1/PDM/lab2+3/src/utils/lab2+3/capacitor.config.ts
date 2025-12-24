import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.emaple.myApp',
  appName: 'myApp',
  webDir: 'dist',
  server: {
    url: 'http://localhost:8100',
    cleartext: true
  },
  plugins: {
    Camera: {
      allowEditing: false,
      saveToGallery: false,
      presentationStyle: 'fullscreen'
    }
  }
};

export default config;
