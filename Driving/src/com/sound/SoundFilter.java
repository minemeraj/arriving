package com.sound;


class SoundFilter {
	
	private int frameSize=0;
	
	SoundFilter(int frameSize) {
		this.frameSize=frameSize;
	}

	byte[] filter(byte[] data,double modulation){
		
		int length=data.length;
		int dim=(int) (length*modulation);
		dim-=dim%frameSize;
		byte[] newData=new byte[dim];
		
		for(int i=0;i<dim;i+=frameSize){
		
			int index=(i*length)/(dim-1);
			index=index-index%frameSize;
			short sample = getSample(data,index);
			setSample(newData,i,sample);
			
		}
		return newData;
	}
	//16 bit sample
	private static short getSample(byte[] buffer, int position) {
		return (short)(
				((buffer[position+1] & 0xff) << 8) |
				(buffer[position] & 0xff));
	}
	//16 bit sample
	private static void setSample(byte[] buffer, int position,
			short sample)
	{
		buffer[position] = (byte)(sample & 0xff);
		buffer[position+1] = (byte)((sample >> 8) & 0xff);
	}

		
}
