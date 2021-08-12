package dustren.main;

import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle.EnumTitleAction;

@SuppressWarnings("unused")
public class NmsManager {
	public static void sendActionBar(Player p, String message) {
		EntityPlayer e = ((CraftPlayer) p).getHandle();

		String msg = String.format("{\"text\":\"%s\"}", message);
		IChatBaseComponent icbc = ChatSerializer.a(msg);
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, icbc, 20, 20, 20);
		e.playerConnection.sendPacket(packet);
	}
	
	public static void sendTitle(Player p, String message) {
		EntityPlayer e = ((CraftPlayer) p).getHandle();

		String msg = String.format("{\"text\":\"%s\"}", message);
		IChatBaseComponent icbc = ChatSerializer.a(msg);
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, icbc, 20, 20, 20);
		e.playerConnection.sendPacket(packet);
	}
	
	public static void sendSubtitle(Player p, String message) {
		EntityPlayer e = ((CraftPlayer) p).getHandle();

		String msg = String.format("{\"text\":\"%s\"}", message);
		IChatBaseComponent icbc = ChatSerializer.a(msg);
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, icbc, 20, 20, 20);
		e.playerConnection.sendPacket(packet);
	}
}
