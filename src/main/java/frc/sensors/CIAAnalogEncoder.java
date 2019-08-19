package frc.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

/*
Modified 2019 Season by Christopher Hess
*/

public class CIAAnalogEncoder {
	
	private AnalogInput encoder; //Creates the input
	private double workingZero = 0; //Creates aworking zero
	private boolean inverted = false; //Used if inverted
	private double unpluggedVoltage = 0; //Sets the unplugged voltage
	private double angle = 0.00;
	
	public CIAAnalogEncoder(int analogPort){ //Creater
		encoder = new AnalogInput(analogPort);
	}
	
	public void setWorkingZero(double newWorkingZero){ //Used to set wroking zero
		workingZero = newWorkingZero;
	}
	
	public void setUnpluggedVoltage(double newUnpluggedVoltage){ //Used to set unplugged voltage
		unpluggedVoltage = newUnpluggedVoltage;
	}
	
	public void setInverted(boolean newInverted){ //Used to invert
		inverted = newInverted;
	}
	
	public double getAngle(){ //Used to get the angele
		this.angle = encoder.getVoltage()*72 - workingZero;
		if(angle > 346) this.angle -= 346;
		if(inverted) return -this.angle;
		else return angle;
	}
		
	public double getRawVoltage(){ //Used to get raw voltage
		return encoder.getVoltage();
	}

	public boolean isConnected(){ //Checks to see if connected (Uses unplugged voltage)
		if(Math.abs(encoder.getVoltage() - unpluggedVoltage) < 0.1) return false;
		else return true;
	}
}