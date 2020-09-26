package sx3Configuration.util;

public class UACSettingFieldConstants {

	public static final String[] CHANNEL_CONFIGURATION_FIELDS = { "D0: Left Front (L)", "D1: Right Front (R)",
			"D2: Center Front (C)", "D3: Low Frequency Enhancement (LFE)", "D4: Left Surround (LS)",
			"D5: Right Surround (RS)", "D6: Left of Center (LC)", "D7: Right of Center (RC)", "D8: Surround (S)",
			"D9: Side Left (SL)", "D10: Side Right (SR)", "D11: Top (T)" };

	public static final String[] FEATURE_UNIT_CONTROLS_FIELDS = { "D0: Mute", "D1: Volume", "D2: Bass",
			"D3: Mid", "D4: Treble", "D5: Graphic Equalizer", "D6: Automatic Gain", "D7: Delay", "D8: Bass Boost", "D9: Loudness"};

	public static final String[] TERMINAL_TYPE = { "Microphone" };

	public static final String[] AUDIO_FORMAT = { "UNDEFINED", "PCM", "PCM8", "IEEE_FLOAT", "ALAW", "MULAW" };

	public static final String[] BIT_RESOLUTION = { "8 bit", "16 bit", "24 bit", "32 bit" };

	public static final String[] SAMPLING_FREQUENCIES = { "Not Used", "40000", "44100", "48000", "96000", "192000" };
	
}
