package powerglove

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.ItemUsageContext
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import net.minecraft.world.World
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PowerGloveItem(settings: Settings) : Item(settings) {
    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        val world: World = context!!.world
        val state: BlockState = world.getBlockState(context.blockPos)
        val block: Block = state.block
        if (!world.isClient) {
            world.scheduleBlockTick(context.blockPos, block, 4)
        }
        return super.useOnBlock(context)
    }
}

object PowerGlove : ModInitializer {
    private val LOGGER: Logger = LoggerFactory.getLogger("powerglove")
    private val POWER_GLOVE_ITEM: Item = PowerGloveItem(FabricItemSettings().maxCount(1))

    override fun onInitialize() {
        LOGGER.debug("Power Glove initialized!")
        Registry.register(Registries.ITEM, Identifier("powerglove", "power_glove"), POWER_GLOVE_ITEM)
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register {it.add(POWER_GLOVE_ITEM)}
    }
}