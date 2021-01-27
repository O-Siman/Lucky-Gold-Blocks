package cchs.codingclub.luckygoldblock;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BlockBreakListener implements Listener {
    //Random list of things to spawn
    ArrayList<EntityType> luckyEntities = new ArrayList<>(Arrays.asList(EntityType.BAT, EntityType.CHICKEN, EntityType.EVOKER));

    @EventHandler
    public void onBreakBreak(BlockBreakEvent event) {
        //Return if block broken is not a gold block
        if (!event.getBlock().getType().equals(Material.GOLD_BLOCK))
            return;

        /*
         * 0. Spawn bat
         * 1. Spawn evoker
         * 2. Spawn (evil) chicken
         * 3. Drop diamond block
         * 4. Super zombie
         * 5. Special potion
         * */

        //Vars
        Random random = new Random();
        Player player = event.getPlayer();
        World world = event.getPlayer().getWorld();
        Location blockBrokenLocation = event.getBlock().getLocation();

        int randomInt = random.nextInt(6);

        switch (randomInt) {
            case 0: {
//                0. Spawn bat
                world.spawnEntity(blockBrokenLocation, EntityType.BAT);
                break;
            }
            case 1: {
                world.spawnEntity(blockBrokenLocation, EntityType.EVOKER);
                break;
//        * 1. Spawn evoker
            }
            case 2: {
                world.spawnEntity(blockBrokenLocation, EntityType.CHICKEN);
                break;
//        * 2. Spawn chicken
            }
            case 3: {
                world.dropItem(blockBrokenLocation, new ItemStack(Material.DIAMOND_BLOCK));
                break;
//        * 3. Drop diamond block
            }
            case 4: {
//                    * 4. Super zombie
                Entity superZombie = world.spawnEntity(blockBrokenLocation, EntityType.ZOMBIE);

                //Armor
                ItemStack[] armorArray = {
                        new ItemStack(Material.DIAMOND_BOOTS),
                        new ItemStack(Material.DIAMOND_LEGGINGS),
                        new ItemStack(Material.DIAMOND_CHESTPLATE),
                        new ItemStack(Material.DIAMOND_HELMET)
                };

                armorArray[0].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                armorArray[1].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                armorArray[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                armorArray[3].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

                LivingEntity livingZombie = ((LivingEntity) superZombie);

                livingZombie.setCustomName("Bob");

                livingZombie.getEquipment().setArmorContents(armorArray);
                livingZombie.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
                break;
            }
            case 5: {
                ItemStack potionItemStack = new ItemStack(Material.POTION);

                PotionMeta potionMeta = (PotionMeta) potionItemStack.getItemMeta();
                potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 3), true);
                potionMeta.setDisplayName("Flying Potion");
                potionMeta.setColor(Color.SILVER);

                potionItemStack.setItemMeta(potionMeta);

                world.dropItem(blockBrokenLocation, potionItemStack);
                break;
//        * 5. Special potion
            }
        }
        event.setDropItems(false);
    }
}
