const { remote } = require('webdriverio')

async function runTestBottomNavigation(driver) {
    try {
        await driver.pause(1000);
        const bottomNavigation = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[3]');
        if (await bottomNavigation.isDisplayed()) {
            const newsLettersCreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[3]/android.view.View[1]/android.view.View[2]');
            newsLettersCreen.click();
            await driver.pause(1000);

            const profileScreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[3]/android.view.View[3]/android.view.View[2]');
            profileScreen.click();
            await driver.pause(1000);

            const closetScreen = await driver.$('//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[3]/android.view.View[2]/android.view.View[2]');
            closetScreen.click();
            await driver.pause(1000);
        } else {
            throw new Error('❌ DISPLAYED-ERROR: El bottom navigation no es visible ❌');
        }
        console.log("✅ Test completado con éxito. BOTTOM NAVIGATION ✅");
    } catch (error) {
    throw error;
    }
}

module.exports = { runTestBottomNavigation };