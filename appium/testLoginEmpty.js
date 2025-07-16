const { remote } = require('webdriverio');

async function runTestLogin(driver) {
    try {
        await driver.pause(1000);

        const loginButton = await driver.$('//android.widget.Button');
        await loginButton.click();
        await driver.pause(3000);

        // Verificar que el inicio de sesión ha fallado porque que se muestra la alerta de campos vacíos
        console.log('Test: email y contraseña inválidos por campos vacíos OK');
        const errorMessage = await driver.$('//android.view.ViewGroup/android.view.View/android.view.View/android.view.View');
        if (await errorMessage.isDisplayed()) {
            console.log('Mensaje de error visible: "Error de inicio de sesión por campos vacíos"');
            const buttonCloseALert = await driver.$('//android.widget.Button')
            await buttonCloseALert.click();
            await driver.pause(1500);
        } else {
            throw new Error('El mensaje de error no es visible');
        }
        console.log("Test completado con éxito. EMPTYLOGIN");

    } catch (error) {
        throw error;

    }
}

module.exports = { runTestLogin };
