package net.mgstudios.batteryinfo.util;

import com.sun.jna.Native;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class BatterySupplier {
    public static final class SYSTEM_POWER_STATUS extends Structure {
        public byte ACLineStatus;
        public byte BatteryFlag;
        public byte BatteryPercent;
        public byte Reserved1;
        public int BatteryLifeTime;
        public int BatteryFullLifeTime;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("ACLineStatus", "BatteryFlag", "BatteryPercent", "Reserved1", "BatteryLifeTime", "BatteryFullLifeTime");
        }
    }

    public interface Kernel32 extends com.sun.jna.Library {
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

        boolean GetSystemPowerStatus(SYSTEM_POWER_STATUS result);
    }

    public static int getLevel() {
        final SYSTEM_POWER_STATUS status = new SYSTEM_POWER_STATUS();
        final boolean isSuccess = Kernel32.INSTANCE.GetSystemPowerStatus(status);

        if (isSuccess) {
            return status.BatteryPercent;
        } else {
            return -1;
        }
    }
}