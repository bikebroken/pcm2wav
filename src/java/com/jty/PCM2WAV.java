package com.jty;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.jty.WaveHeader;
/**
 * PCM
 *
 * @author qkb
 */
 
public class PCM2WAV {
	
	/**
	 * 
	 * @param src 
	 * @param target 
	 * @throws Exception
	 */
	public static void convertAudioFiles(String src, String target) throws Exception {
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(target);

		//
		byte[] buf = new byte[1024 * 4];
		int size = fis.read(buf);
		int PCMSize = 0;
		while (size != -1) {
			PCMSize += size;
			size = fis.read(buf);
		}
		fis.close();

		//
		WaveHeader header = new WaveHeader();
		//
		header.fileLength = PCMSize + (44 - 8);
		header.FmtHdrLeth = 16;
		header.BitsPerSample = 16;
		header.Channels = 2;
		header.FormatTag = 0x0001;
		header.SamplesPerSec = 44100;
		header.BlockAlign = (short)(header.Channels * header.BitsPerSample / 8);
		header.AvgBytesPerSec = header.BlockAlign * header.SamplesPerSec;
		header.DataHdrLeth = PCMSize;

		byte[] h = header.getHeader();

		assert h.length == 44; //
		//write header
		fos.write(h, 0, h.length);
		//write data stream
		fis = new FileInputStream(src);
		size = fis.read(buf);
		while (size != -1) {
			fos.write(buf, 0, size);
			size = fis.read(buf);
		}
		fis.close();
		fos.close();
		System.out.println("Convert OK!");
    }

    //
    public static void main(String[] agrs) throws Exception{
        convertAudioFiles("C:\\Users\\hasee\\Desktop\\xml.pcm", "C:\\Users\\hasee\\Desktop\\xml.wav");
    }
}