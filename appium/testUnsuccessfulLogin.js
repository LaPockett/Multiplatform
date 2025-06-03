const { remote } = require('webdriverio')

const capabilities = {
    platformName: 'Android',
    'appium:automationName': 'UiAutomator2',
    'appium:deviceName': 'Android',
    'appium:udid': '1e27529b',
    'appium:appPackage': 'com.dian.prueba',
    'appium:appActivity': 'com.dian.prueba.view.MainActivity',
    'appium:autoGrantPermissions': true
};
const wdOpts = {
    hostname: process.env.APPIUM_HOST || 'localhost',
    port: parseInt(process.env.APPIUM_PORT, 10) || 4723,
    logLevel: 'info',
    capabilities,
};

async function runTestLogin() {
    const driver = await remote(wdOpts);
    try {
        await driver.pause(1000);

        const emailField = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]');
        await emailField.setValue('usuarioDian');
        let emailValue = await emailField.getText();

        const passwordField = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]');
        await passwordField.setValue('12345');
        let passwordValue = await passwordField.getText();

        const loginButton = await driver.$('//android.widget.Button');
        await loginButton.click();
        await driver.pause(3000);

        // Verificar que el inicio de sesión ha fallado porque que se muestra la alerta de error
        console.log('Test: email y contraseña inválidos OK');
        const errorMessage = await driver.$('//android.view.ViewGroup/android.view.View/android.view.View/android.view.View');
        if (await errorMessage.isDisplayed()) {
            console.log('Mensaje de error visible: "Error de inicio de sesión"');
            const buttonOKALert = await driver.$('//android.widget.Button')
            await buttonOKALert.click();
            await driver.pause(1500);
        } else {
            throw new Error('El mensaje de error no es visible');
        }

    } catch (error) {
        console.error('Error en el test:', error);
        const screenshot = await driver.takeScreenshot();
        require('fs').writeFileSync('screenshot_error.png', screenshot, 'base64');
        throw error;

    } finally {
        await driver.deleteSession();
    }
}

runTestLogin().catch(console.error);
