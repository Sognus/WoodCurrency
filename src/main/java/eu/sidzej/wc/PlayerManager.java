package eu.sidzej.wc;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.sidzej.wc.config.Config;
import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.utils.TimeUtils;

public class PlayerManager {
	private static final PlayerManager instance = new PlayerManager();
	private HashMap<UUID, PlayerData> players;

	private PlayerManager() {
		players = new HashMap<UUID, PlayerData>();
	}

	public static PlayerManager getInstance() {
		return instance;
	}

	public static PlayerData getPlayerData(UUID uuid) {
		if (instance.players.containsKey(uuid))
			return instance.players.get(uuid);
		else{
			PlayerData data = DBUtils.getPlayerData(uuid);
			if( data == null){
				DBUtils.registerPlayer(uuid);
				data = DBUtils.getPlayerData(uuid);
			}
			return data;
		}
			
	}

	public static PlayerData getPlayerData(String name){
		for(Player p: Bukkit.getOnlinePlayers()){
			if(p.getName().equals(name))
				return getPlayerData(p);
		}
		return null;
	}
	
	public static PlayerData getPlayerData(Player player) {
		return getPlayerData(player.getUniqueId());
	}

	public static PlayerData addPlayer(UUID uuid, int id, int day, int total, byte tier,
			Timestamp timestamp, boolean ban) {
		PlayerData data = new PlayerData(uuid,id, day, total, tier, timestamp, ban);
		instance.players.put(uuid, data);
		return data;
	}

	public static class PlayerData {
		private int id, day, total;
		private byte tier;
		private Calendar timestamp;
		private boolean blocked;
		private UUID uuid;

		public PlayerData(UUID uuid,int id, int day, int total, byte tier, Timestamp timestamp, boolean blocked) {
			this.uuid = uuid;
			this.id = id;
			this.day = day;
			this.total = total;
			this.tier = tier;
			this.timestamp = TimeUtils.getCalendar(timestamp);
			this.blocked = blocked;
		}

		public UUID getUUID(){
			return uuid;
		}
		
		public int getItemLeft() {
			return (int) (Config.MAX_SELL_PER_DAY / Math.pow(2, tier - 1) - day);
		}

		public void addCount(int amount) {
			day += amount;
			total += amount;
		}

		public int getID() {
			return id;
		}

		public int getDay() {
			return day;
		}

		public int getTotal() {
			return total;
		}

		public Calendar getDate() {
			return timestamp;
		}

		public int getTier() {
			return tier;
		}

		public void resetDay() {
			day = 0;
		}

		public void incrementTier() {
			if (tier < 6)
				tier++;
		}

		public void decrementTier() {
			if (tier > 1)
				tier--;
		}

		public Timestamp getTimestamp() {
			return TimeUtils.getTimestamp(timestamp);
		}

		public void setBlocked(boolean b) {
			blocked = b;
		}
		public boolean getBlocked() {
			return blocked;
		}
	}

	public static void remove(Player p) {
		instance.players.remove(p.getUniqueId());		
	}
}
