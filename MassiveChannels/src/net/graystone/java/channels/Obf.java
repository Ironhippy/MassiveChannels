package net.graystone.java.channels;

import java.util.List;
import java.util.Random;

import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.MUtil;

import net.graystone.java.channels.entity.MChannel;
import net.graystone.java.channels.entity.MPlayer;

public class Obf
{
	public static String obfuscate(String str, MChannel channel, MPlayer from, MPlayer to)
	{
		int inner = (int) channel.getInnerRadius();
		int outer = (int) channel.getOuterRadius();
		
		if (str.endsWith("!!")) { outer = (int) channel.getOuterShoutRadius(); inner = (int) channel.getInnerShoutRadius(); }
		if (str.endsWith("!")) { outer = (int) channel.getOuterYellRadius(); inner = (int) channel.getInnerYellRadius(); }
		if (str.endsWith("^")) { outer = (int) channel.getOuterWhisperRadius(); inner = (int) channel.getInnerWhisperRadius(); }
		if (str.endsWith("]")||str.endsWith(")")||str.endsWith("-")||str.endsWith("*")||str.endsWith("+")||str.endsWith("=")) inner = outer--;
		
		double distanceBetween = PS.locationDistance(PS.valueOf(from.getPlayer()), PS.valueOf(to.getPlayer()));
				
		int outerRange = (outer-inner);
		
		StringBuilder obfBuilder = new StringBuilder(str);
		
		if ((int) (distanceBetween)<=inner) return str;
		
		if (outerRange>0) {
			for (int i = 0; i<str.length(); i++)
			{	
				int r = new Random().nextInt(outerRange);
			
				int dis = (int) (outer-distanceBetween);
			
				if (r<dis) continue;
			
				if (specialCharacters().contains(obfBuilder.charAt(i))) continue;
			
				if (obfBuilder.charAt(i)=='.') continue;
				
				obfBuilder.setCharAt(i, '.');
			}
		
			for (int i = 0; i<str.length(); i++)
			{
				int r = new Random().nextInt(outerRange);
			
				int dis = (int) (outer-distanceBetween);
			
				if (r<dis) continue;
			
				if (specialCharacters().contains(obfBuilder.charAt(i))) continue;
			
				if (obfBuilder.charAt(i)!='.') continue;
				
				obfBuilder.setCharAt(i, ',');
			}
		}
		
		System.out.println(obfBuilder.toString());
		
		return obfBuilder.toString();
	}
	
	public static List<Character> specialCharacters()
	{
		return MUtil.list('^', '?', '!', '*', ')', '(', '[', ']');
	}
}
