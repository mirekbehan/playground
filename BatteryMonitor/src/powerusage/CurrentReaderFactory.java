/*
 *  Copyright (c) 2010-2011 Ran Manor
 *  
 *  This file is part of CurrentWidget.
 *    
 * 	CurrentWidget is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  CurrentWidget is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with CurrentWidget.  If not, see <http://www.gnu.org/licenses/>.
*/

package powerusage;

import java.io.File;
import java.util.Random;

import android.os.Build;

public class CurrentReaderFactory {
	
	static public Long getValue() {
		
		File f = null;		
	
		// HTC One X
		if (Build.MODEL.toLowerCase().contains("htc one x")) {
			f = new File("/sys/class/power_supply/battery/batt_attr_text");
			if (f.exists()) {
				Long value = BattAttrTextReader.getValue(f, "I_MBAT", "I_MBAT");
				if (value != null)
					return value;
			}			
		}
		
		// wildfire S
		if (Build.MODEL.toLowerCase().contains("wildfire s")) {
			f = new File("/sys/class/power_supply/battery/smem_text");
			if (f.exists()) {
				Long value = BattAttrTextReader.getValue(f, "eval_current", "batt_current");
				if (value != null)
					return value;
			}
		}
		
		// trimuph with cm7, lg ls670, galaxy s3
		if (Build.MODEL.toLowerCase().contains("triumph") ||
				Build.MODEL.toLowerCase().contains("ls670") ||
				Build.MODEL.toLowerCase().contains("gt-i9300")) {
			f = new File("/sys/class/power_supply/battery/current_now");
			if (f.exists()) {
				return OneLineReader.getValue(f, false);
			}
		}
		
		// htc desire hd / desire z / inspire?
		if (Build.MODEL.toLowerCase().contains("desire hd") ||
				Build.MODEL.toLowerCase().contains("desire z") ||
				Build.MODEL.toLowerCase().contains("inspire") ||
				//htc evo view tablet
				Build.MODEL.toLowerCase().contains("pg41200"))  {
			
			f = new File("/sys/class/power_supply/battery/batt_current");
			if (f.exists()) {
				return OneLineReader.getValue(f, false);
			}
		}

		// nexus one cyangoenmod
		f = new File("/sys/devices/platform/ds2784-battery/getcurrent");
		if (f.exists()) {
			return OneLineReader.getValue(f, true);
		}

		// sony ericsson xperia x1
		f = new File("/sys/devices/platform/i2c-adapter/i2c-0/0-0036/power_supply/ds2746-battery/current_now");
		if (f.exists()) {
			return OneLineReader.getValue(f, false);
		}
		
		// xdandroid
		/*if (Build.MODEL.equalsIgnoreCase("MSM")) {*/
			f = new File("/sys/devices/platform/i2c-adapter/i2c-0/0-0036/power_supply/battery/current_now");
			if (f.exists()) {
				return OneLineReader.getValue(f, false);
			}
		/*}*/
	
		// droid eris
		f = new File("/sys/class/power_supply/battery/smem_text");		
		if (f.exists()) {
			Long value = SMemTextReader.getValue();
			if (value != null)
				return value;
		}
		
		// htc sensation / evo 3d
		f = new File("/sys/class/power_supply/battery/batt_attr_text");
		if (f.exists())
		{
			Long value = BattAttrTextReader.getValue(f, "batt_discharge_current", "batt_current");
			if (value != null)
				return value;
		}
		
		// some htc devices
		f = new File("/sys/class/power_supply/battery/batt_current");
		if (f.exists())
			return OneLineReader.getValue(f, false);
		
		// nexus one
		f = new File("/sys/class/power_supply/battery/current_now");
		if (f.exists())
			return OneLineReader.getValue(f, true);
		
		// samsung galaxy vibrant		
		f = new File("/sys/class/power_supply/battery/batt_chg_current");
		if (f.exists())
			return OneLineReader.getValue(f, false);
		
		// sony ericsson x10
		f = new File("/sys/class/power_supply/battery/charger_current");
		if (f.exists())
			return OneLineReader.getValue(f, false);
		
		// Nook Color
		f = new File("/sys/class/power_supply/max17042-0/current_now");
		if (f.exists())
			return OneLineReader.getValue(f, false);
		
		// Xperia Arc
		f = new File("/sys/class/power_supply/bq27520/current_now");
		if (f.exists())
			return OneLineReader.getValue(f, true);
		
		// Motorola Atrix
		f = new File("/sys/devices/platform/cpcap_battery/power_supply/usb/current_now");
		if (f.exists()) 
			return OneLineReader.getValue(f, false);
		
		// Acer Iconia Tab A500
		f = new File("/sys/EcControl/BatCurrent");
		if (f.exists())
			return OneLineReader.getValue(f, false);
		
		// charge current only, Samsung Note
		f = new File("/sys/class/power_supply/battery/batt_current_now");
		if (f.exists())
			return OneLineReader.getValue(f, false);
		
		// galaxy note
		f = new File("/sys/class/power_supply/battery/batt_current_adc");
		if (f.exists())
			return OneLineReader.getValue(f, false);
		
		//sdk
		Random r = new Random();
		
		return (long)r.nextInt(50)+300;
		
		
		//return null;
	}
}