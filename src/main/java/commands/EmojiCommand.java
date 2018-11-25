package commands;

import localisation.Strings;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static util.JDAUtil.sendEmbed;

//TODO: Clean this mess up
public class EmojiCommand implements Command {
	public void execute(List<String> args, MessageReceivedEvent event) {
		if(event.getMessage().getAttachments().size() == 0 && args.size() == 2) {
			try {
				InputStream stream = new URL(args.get(1)).openStream();
				BufferedImage image = scaleImage(ImageIO.read(stream)); //This takes a while
				byte[] data = convertImageToByteArray(image);

				createEmoji(args, event, data);
			} catch (MalformedURLException e) {
				reportException(event, "emoji.malformedUrlTitle", "emoji.malformedUrlDescription");
			} catch (IOException e) {
				reportException(event, "emoji.downloadErrorTitle", "emoji.downloadErrorDescription");
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
					reportException(event,"emoji.onlyImagesTitle","emoji.onlyImagesDescription");
				}
			} catch (IOException e) {
				reportException(event, "emoji.downloadErrorTitle", "emoji.downloadErrorDescription");
			}
		} else {
			reportException(event, "error", "emoji.syntax");
		}
		event.getMessage().delete().queue();
	}

	private void createEmoji(List<String> args, MessageReceivedEvent event, byte[] bytes) {
		event.getGuild().getController().createEmote(args.get(0), Icon.from(bytes)).queue(emote ->
				event.getChannel().sendMessage(sendEmbed(Color.GREEN,
						Strings.getString("success", Strings.Lang.EN),
						Strings.getString("emoji.success", Strings.Lang.EN)
				)).queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS)));
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

	private void reportException(MessageReceivedEvent event, String s, String s2) {
		event.getChannel().sendMessage(sendEmbed(Color.RED,
				Strings.getString(s, Strings.Lang.EN),
				Strings.getString(s2, Strings.Lang.EN)))
				.queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
	}

	@Override
	public String getName() {
		return "emoji";
	}

	@Override
	public String help() {
		return null;
	}
}
