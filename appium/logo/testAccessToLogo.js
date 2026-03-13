const { remote } = require('webdriverio')

async function runTestAccessToLogo(driver) {
    try {
        await driver.pause(5000);
        const accessButton = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View/android.widget.Button');
        if (await accessButton.isDisplayed()) {
            await accessButton.click();
            await driver.pause(1000);
        } else {
            throw new Error('❌ DISPLAY-ERROR: El botón de acceso no se encontró o no está visible ❌');
        }
        console.log("✅ Test completado con éxito. ACCESS TO LOGO ✅");
    } catch (error) {
    throw error;
    }
}

module.exports = { runTestAccessToLogo };