package eu.sidzej.wc.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum TreeSpecies {
		OAK(1),BIRCH(2),SPRUCE(3),JUNGLE(4),DARK(5),ACACIA(6);
		
		int i;
		private TreeSpecies(int i){
			this.i = i;
		}
		
		public int get(){
			return i;
		}

		public ItemStack toItemStack() {
			return toItemStack(1);
		}

		public ItemStack toItemStack(int amount) {
			ItemStack stack = null;

			switch(this)
			{
				case OAK:
					stack = new ItemStack(Material.OAK_LOG);
					break;
				case BIRCH:
					stack = new ItemStack(Material.BIRCH_LOG);
					break;
				case SPRUCE:
					stack = new ItemStack(Material.SPRUCE_LOG);
					break;
				case JUNGLE:
					stack = new ItemStack(Material.JUNGLE_LOG);
					break;
				case DARK:
					stack = new ItemStack(Material.DARK_OAK_LOG);
					break;
				case ACACIA:
					stack = new ItemStack(Material.ACACIA_LOG);
					break;
			}
			stack.setAmount(amount);
			return stack;
		}
}
