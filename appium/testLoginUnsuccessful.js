const { remote } = require('webdriverio')

async function runTestLogin(driver) {
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
        console.log("Test completado con éxito. LOGIN NO VÁLIDO");
        const errorMessage = await driver.$('//android.view.ViewGroup/android.view.View/android.view.View/android.view.View');
        if (await errorMessage.isDisplayed()) {
            console.log('Mensaje de error visible: "Error de inicio de sesión"');
            const buttonOKALert = await driver.$('//android.widget.Button')
            await buttonOKALert.click();
            await driver.pause(1500);
        } else {
            throw new Error('El mensaje de error no es visible');
        }
        console.log("Test completado con éxito. UNSUCCESSFULLOGIN");

    } catch (error) {
        throw error;

    }
}

module.exports = { runTestLogin };
