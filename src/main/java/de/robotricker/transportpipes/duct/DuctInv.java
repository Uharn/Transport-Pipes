package de.robotricker.transportpipes.duct;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import de.robotricker.transportpipes.TransportPipes;
import de.robotricker.transportpipes.utils.staticutils.InventoryUtils;

public abstract class DuctInv implements Listener {

	protected Duct duct;

	public DuctInv(Duct duct) {
		this.duct = duct;
		Bukkit.getPluginManager().registerEvents(this, TransportPipes.instance);
	}

	public Duct getDuct() {
		return duct;
	}

	public abstract void openOrUpdateInventory(Player p);
	
	protected abstract void populateInventory(Player p, Inventory inventory);

	/**
	 * @return wether the event to be cancelled
	 */
	protected abstract boolean notifyInvClick(Player p, int rawSlot, Inventory inventory);

	protected abstract void notifyInvSave(Player p, Inventory inventory);

	protected abstract boolean containsInventory(Inventory inventory);

	public ItemStack createItem(Material material) {
		return createItem(material, 1);
	}

	public ItemStack createItem(Material material, int amount) {
		return createItem(material, 1, 0);
	}

	public ItemStack createItem(Material material, int amount, int data) {
		return new ItemStack(material, amount, (short) data);
	}

	public ItemStack createItem(Material material, int amount, int data, String displayName, String... lore) {
		return InventoryUtils.changeDisplayNameAndLore(new ItemStack(material, amount, (short) data), displayName, lore);
	}

	public ItemStack createItemFromConfig(Material material, int amount, int data, String displayName, List<String> lore) {
		return InventoryUtils.changeDisplayNameAndLoreConfig(new ItemStack(material, amount, (short) data), displayName, lore);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory() != null && containsInventory(e.getInventory()) && e.getWhoClicked() instanceof Player) {
			// clicked on glass pane
			if (InventoryUtils.isGlassItemOrBarrier(e.getCurrentItem())) {
				e.setCancelled(true);
				return;
			}
			if (notifyInvClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getInventory())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getInventory() != null && containsInventory(e.getInventory()) && e.getPlayer() instanceof Player) {
			notifyInvSave((Player) e.getPlayer(), e.getInventory());
		}
	}

}
