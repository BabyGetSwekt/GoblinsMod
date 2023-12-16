package goblinbanaan.features.miscellaneous;

import goblinbanaan.config.GoblinConfig;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static goblinbanaan.utils.Utils.checkForSkyblock;
import static goblinbanaan.utils.Utils.drawTitle;

public class HealthCheck {

    protected boolean reminderCooldown = false;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String chatMessage = event.message.getUnformattedText();
        System.out.println(chatMessage);

        if (GoblinConfig.healReminder &&
                (chatMessage.startsWith("§6") || chatMessage.startsWith("§c")) &&
                chatMessage.endsWith("✎ Mana") && checkForSkyblock()) {
            System.out.println("HEAL REMINDER PASSED");

            int heartIndex = chatMessage.indexOf("❤");
            if (heartIndex == -1) return;

            String hpInfo = chatMessage.substring(0, heartIndex);
            int hpStartIndex = hpInfo.lastIndexOf('§');
            if (hpStartIndex == -1) return;

            hpInfo = hpInfo.substring(hpStartIndex + 1);
            String[] hpParts = hpInfo.split("/");

            int currentHP = Integer.parseInt(hpParts[0].replaceAll("[^0-9]", ""));
            int maxHP = Integer.parseInt(hpParts[1].replaceAll("[^0-9]", ""));
            currentHP = removeFirstDigit(currentHP);


            if (GoblinConfig.healReminder && GoblinConfig.healthPercentage != 0) {
                System.out.println("Heal percentage is: " + GoblinConfig.healthPercentage);
                if (currentHP <= (maxHP * GoblinConfig.healthPercentage)) {
                    System.out.println("Drawing a title ");
                    drawTitle("Heal up!", null, 30);
                    System.out.println("Drawing a title 2");
                }
            }
        }
    }

    private int removeFirstDigit(int number) {
        // Convert number to string, remove first character, and convert back to int
        String numberString = Integer.toString(number);
        numberString = numberString.substring(1);
        return Integer.parseInt(numberString);
    }
}