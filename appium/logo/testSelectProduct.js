const { remote } = require('webdriverio')

async function runTestSelectProduct(driver) {
    try {
        await driver.pause(1000);
        const product = await driver.$('(//android.view.View[@content-desc="IMAGE"])[2]');
        if (await product.isDisplayed()) {
            product.click();
            await driver.pause(1000);
            const firstVariantImage = await driver.$('(//android.widget.ImageView[@content-desc="Zoomable Image"])[1]');
            await driver.pause(1000);
            if (await !firstVariantImage.isDisplayed()) {
                throw new Error('❌ DISPLAY-ERROR: La primera imagen no es visible ❌');
            }
        } else {
            throw new Error('❌ DISPLAY-ERROR: El producto no es visible ❌');
        }
        console.log("✅ Test completado con éxito. SELECT PRODUCT ✅");
    } catch (error) {
    throw error;
    }
}

module.exports = { runTestSelectProduct };