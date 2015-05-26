package be.seveningful.sevsrush;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.RESET;
import static org.bukkit.ChatColor.YELLOW;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import be.seveningful.sevsrush.countdown.CountDown;
import be.seveningful.sevsrush.messages.ActionMessages;
import be.seveningful.sevsrush.messages.DeathMessages;
import be.seveningful.sevsrush.messages.PlayerTeam;
import be.seveningful.sevsrush.util.Effects;

public class EventsClass implements Listener {
	static Main plugin;
	public static ArrayList<Team> teamsalive = new ArrayList<Team>();

	public EventsClass(Main main) {
		this.plugin = main;
	}

	public static void addItem(Inventory inv, ChatColor ccolor, DyeColor color,
			String Name, int slot) {
		ItemStack team = new ItemStack(Material.BANNER);
		BannerMeta meta = (BannerMeta) team.getItemMeta();
		meta.setDisplayName(ccolor + Name);
		if (color.equals(DyeColor.RED)) {
			List<String> lore = new ArrayList<String>();

			for (OfflinePlayer pl : plugin.s.getTeam("Rouge").getPlayers()) {
				lore.add(ChatColor.RED + "- " + pl.getName());
			}

			meta.setLore(lore);
		} else if (color.equals(DyeColor.YELLOW)) {
			List<String> lore = new ArrayList<String>();
			for (OfflinePlayer pl : plugin.s.getTeam("Jaune").getPlayers()) {
				lore.add(ChatColor.YELLOW + "- " + pl.getName());
			}
			meta.setLore(lore);
		} else if (color.equals(DyeColor.BLUE)) {
			List<String> lore = new ArrayList<String>();
			for (OfflinePlayer pl : plugin.s.getTeam("Bleue").getPlayers()) {
				lore.add(ChatColor.BLUE + "- " + pl.getName());
			}
			meta.setLore(lore);
		} else if (color.equals(DyeColor.GREEN)) {
			List<String> lore = new ArrayList<String>();
			for (OfflinePlayer pl : plugin.s.getTeam("Verte").getPlayers()) {
				lore.add(ChatColor.GREEN + "- " + pl.getName());
			}
			meta.setLore(lore);
		}
		meta.setBaseColor(color);

		team.setItemMeta(meta);
		inv.setItem(slot, team);
	}

	public static void openTeamInv(Player p) {
		Inventory inv = Bukkit.createInventory(p, 9 * 1, GOLD
				+ "Choisir son Équipe");

		addItem(inv, ChatColor.RED, DyeColor.RED, "Équipe Rouge", 0);
		addItem(inv, YELLOW, DyeColor.YELLOW, "Équipe Jaune", 1);
		if (plugin.getConfig().getInt("teams") == 3) {
			addItem(inv, ChatColor.BLUE, DyeColor.BLUE, "Équipe Bleue", 2);
		} else if (plugin.getConfig().getInt("teams") == 4) {
			addItem(inv, ChatColor.BLUE, DyeColor.BLUE, "Équipe Bleue", 2);
			addItem(inv, GREEN, DyeColor.GREEN, "Équipe Verte", 3);
		}

		// Ouvrir Inventaire
		p.openInventory(inv);
	}

	@EventHandler
	public void death(PlayerDeathEvent e) {
		e.getEntity().spigot().respawn();
		e.setDeathMessage(null);
		
		

	}

	@EventHandler
	public void Respawn(PlayerRespawnEvent e) {
		final Player player = e.getPlayer();
		boolean isbed = e.isBedSpawn();
		
		
		if (!isbed) {
			DeathMessages.finalDeath(player);
			PlayerTeam.getTeam(e.getPlayer()).removePlayer(e.getPlayer());
			try {
				Thread.sleep(10);
				;
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BukkitTask task = new BukkitRunnable() {

				@Override
				public void run() {
					player.setGameMode(GameMode.SPECTATOR);

				}
			}.runTaskLater(plugin, 5);

		} else {
			DeathMessages.normalDeath(player);
		}

	}

	public void clearInv(Player player) {
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.getInventory().clear();
	}

	@EventHandler
	public void MoveVoid(PlayerMoveEvent e ){
		int y = (int) e.getTo().getY();
		if(y < 0){
			if(!plugin.rush.rushisstarted){
			e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), plugin.getConfig().getInt(
					"lobby.X"), plugin.getConfig().getInt("lobby.Y"), plugin
					.getConfig().getInt("lobby.Z")));
			}else{
				e.getPlayer().damage(10000);
			}
		}
	}
	
	@EventHandler
	public void SpawnBed(PlayerBedEnterEvent e){
		
		e.setCancelled(true);
		Effects.partyeffects(Sound.SUCCESSFUL_HIT, 10);
		e.getPlayer().setBedSpawnLocation(e.getBed().getLocation(), false);
		
		
	}
	@EventHandler
	public void Hunger(FoodLevelChangeEvent e){
		if(!plugin.rush.rushisstarted){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ChoiceTeam(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getInventory().getName().equals(GOLD + "Choisir son équipe")) {
			if (e.getCurrentItem().getType() == Material.BANNER) {
				BannerMeta banner = (BannerMeta) e.getCurrentItem()
						.getItemMeta();

				if (banner.getBaseColor() == DyeColor.RED) {
					if (plugin.rouge.getPlayers().size() < plugin.getConfig()
							.getInt("players-per-team")) {
						p.sendMessage(plugin.rouge.getPrefix() + "-" + RESET
								+ " Vous avez rejoint l'équipe Rouge");
						plugin.rouge.addPlayer(p);
					} else {
						p.sendMessage(ChatColor.RED
								+ "Cette équipe est complète !");
					}
				} else if (banner.getBaseColor() == DyeColor.YELLOW) {
					if (plugin.jaune.getPlayers().size() < plugin.getConfig()
							.getInt("players-per-team")) {
						p.sendMessage(plugin.jaune.getPrefix() + "-" + RESET
								+ " Vous avez rejoint l'équipe Jaune");
						plugin.jaune.addPlayer(p);
					} else {
						p.sendMessage(ChatColor.RED
								+ "Cette équipe est complète !");
					}
				} else if (banner.getBaseColor() == DyeColor.BLUE) {
					if (plugin.bleue.getPlayers().size() < plugin.getConfig()
							.getInt("players-per-team")) {
						p.sendMessage(plugin.bleue.getPrefix() + "-" + RESET
								+ " Vous avez rejoint l'èquipe Bleue");
						plugin.bleue.addPlayer(p);
					} else {
						p.sendMessage(ChatColor.RED
								+ "Cette équipe est complète !");
					}
				} else if (banner.getBaseColor() == DyeColor.GREEN) {
					if (plugin.verte.getPlayers().size() < plugin.getConfig()
							.getInt("players-per-team")) {

						p.sendMessage(plugin.verte.getPrefix() + "-" + RESET
								+ " Vous avez rejoint l'équipe Verte");
						plugin.verte.addPlayer(p);
					}
				}
				e.setCancelled(true);
				openTeamInv(p);
				p.setDisplayName(plugin.s.getPlayerTeam(p).getPrefix()
						+ p.getName() + plugin.s.getPlayerTeam(p).getSuffix());
			} else if (e.getCurrentItem().getType() == Material.BARRIER) {
				e.setCancelled(true);
				plugin.teleportServer(p,
						plugin.getConfig().getString("fallback-server"));

			}
			if (e.getCurrentItem().getType() == Material.BARRIER) {
				e.setCancelled(true);
				plugin.teleportServer(p,
						plugin.getConfig().getString("fallback-server"));

			}

		}
	}

	@EventHandler
	public void Join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!Game.rushisstarted) {
			p.teleport(new Location(p.getWorld(), plugin.getConfig().getInt(
					"lobby.X"), plugin.getConfig().getInt("lobby.Y"), plugin
					.getConfig().getInt("lobby.Z")));
			p.getInventory().setItem(0, new ItemStack(Material.BANNER, 1));
			p.setGameMode(GameMode.ADVENTURE);

			ItemMeta meta1 = p.getInventory().getItem(0).getItemMeta();
			meta1.setDisplayName(GOLD + "Choisir son équipe");
			p.getInventory().getItem(0).setItemMeta(meta1);

			p.getInventory().setItem(8, new ItemStack(Material.BARRIER, 1));
			ItemMeta meta2 = p.getInventory().getItem(8).getItemMeta();
			meta2.setDisplayName(RED + "Retourner au lobby");
			p.getInventory().getItem(8).setItemMeta(meta2);

			CountDown.countdown("RUSH", plugin);
			e.setJoinMessage(ActionMessages.joinMessage(e.getPlayer()));
			Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective o = s.registerNewObjective(ChatColor.DARK_AQUA
					+ ChatColor.BOLD.toString() + "RUSH", "dummy");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			o.getScore(e.getPlayer().getName()).setScore(0);
			e.getPlayer().setScoreboard(s);
			p.setGameMode(GameMode.ADVENTURE);
		} else {
			e.setJoinMessage(null);
			p.setGameMode(GameMode.SPECTATOR);
		}
	}

	@EventHandler
	public void Leave(PlayerQuitEvent e) {
		e.setQuitMessage(ActionMessages.leaveMessage(e.getPlayer()));
		if (PlayerTeam.hasTeam(e.getPlayer())) {
			PlayerTeam.getTeam(e.getPlayer()).removePlayer(e.getPlayer());
		}
	}

	@EventHandler
	public void choiceTeam(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if (!Game.rushisstarted) {
			if (a.equals(Action.RIGHT_CLICK_AIR)
					|| a.equals(Action.RIGHT_CLICK_BLOCK)) {
				if (p.getItemInHand().getType() == Material.BANNER) {
					if (p.getItemInHand().getItemMeta().getDisplayName()
							.equals(GOLD + "Choisir son équipe")) {
						e.setCancelled(true);

						openTeamInv(p);
						// Ouvrir Inventaire

					} else {

					}
				} else if (p.getItemInHand().getType() == Material.BARRIER) {

					plugin.teleportServer(p,
							plugin.getConfig().getString("fallback-server"));

				}
			} else {

			}
		}

	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		if (!plugin.rush.rushisstarted) {
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void dropItem(EntityDamageEvent e) {
		if (!plugin.rush.rushisstarted) {
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void dropItem(BlockPlaceEvent e) {
		if (!plugin.rush.rushisstarted) {
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void dropItem(BlockBreakEvent e) {
		if (!plugin.rush.rushisstarted) {
			e.setCancelled(true);
		}
	}

}
