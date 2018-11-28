package commands;

import config.User;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.Strings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static util.JDAUtil.sendEmbedWithLocalisation;

//TODO: Clean this mess up
public class EmojiCommand implements Command {
	public boolean execute(List<String> args, MessageReceivedEvent event) {
		if(event.getMessage().getAttachments().size() == 0 && args.size() == 2) {
			try {
				InputStream stream = new URL(args.get(1)).openStream();
				BufferedImage image = scaleImage(ImageIO.read(stream)); //This takes a while
				byte[] data = convertImageToByteArray(image);

				createEmoji(args, event, data);
			} catch (MalformedURLException e) {
				sendEmbedWithLocalisation(Color.RED, "emoji.malformedUrlTitle", "emoji.malformedUrlDescription", event.getTextChannel(), User.loadUser(event.getAuthor().getIdLong()));
			} catch (IOException e) {
				sendEmbedWithLocalisation(Color.RED, "emoji.downloadErrorTitle", "emoji.downloadErrorDescription", event.getTextChannel(), User.loadUser(event.getAuthor().getIdLong()));
			}
		} else if(event.getMessage().getAttachments().size() == 1 && args.size() == 1) {
			try {
				Message.Attachment attachment = event.getMessage().getAttachments().get(0);
				if(attachment.isImage()) {
					attachment.withInputStream(inputStream -> {
						BufferedImage image = ImageIO.read(inputStream);
						byte[] bytes = convertImageToByteArray(scaleImage(image));
						createEmoji(args, event, bytes);
					});
				} else {
					sendEmbedWithLocalisation(Color.RED, "emoji.onlyImagesTitle", "emoji.onlyImagesDescription", event.getTextChannel(), User.loadUser(event.getAuthor().getIdLong()));
				}
			} catch (IOException e) {
				sendEmbedWithLocalisation(Color.RED, "emoji.downloadErrorTitle", "emoji.downloadErrorDescription", event.getTextChannel(), User.loadUser(event.getAuthor().getIdLong()));
			}
		} else {
			sendEmbedWithLocalisation(Color.RED, "error", "emoji.syntax", event.getTextChannel(), User.loadUser(event.getAuthor().getIdLong()));
		}
		return false;
	}

	private void createEmoji(List<String> args, MessageReceivedEvent event, byte[] bytes) {
		event.getGuild().getController().createEmote(args.get(0), Icon.from(bytes)).queue(emote ->
				sendEmbedWithLocalisation(Color.GREEN, "success", "emoji.success", event.getTextChannel(), User.loadUser(event.getAuthor().getIdLong())));
	}

	private BufferedImage scaleImage(BufferedImage src) {
		BufferedImage resizedImage = new BufferedImage(128, 128, src.getType() == 0? BufferedImage.TYPE_INT_ARGB : src.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(src, 0, 0, 128, 128, null);
		g.dispose();

		return resizedImage;
	}

	private byte[] convertImageToByteArray(BufferedImage image) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ImageIO.write( image, "png", stream );
		stream.flush();
		byte[] imageInByte = stream.toByteArray();
		stream.close();
		return imageInByte;
	}

	@Override
	public String getName() {
		return "emoji";
	}

	@Override
	public String getHelp(MessageReceivedEvent event) {
		return Strings.getString("emoji.help",event.getAuthor().getIdLong());
	}
}
