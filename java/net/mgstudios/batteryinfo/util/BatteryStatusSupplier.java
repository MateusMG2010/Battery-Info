package net.mgstudios.batteryinfo.util;

import com.sun.jna.Native;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public final class BatteryStatusSupplier {
    public static final class BatteryStatus extends Structure {
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
    private interface Kernel32 extends com.sun.jna.Library {
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);
        boolean GetSystemPowerStatus(BatteryStatus result);
    }
    public static int getBatteryPercent() {
        final BatteryStatus status = new BatteryStatus();
        if (Kernel32.INSTANCE.GetSystemPowerStatus(status)) return status.BatteryPercent;
        return -1;
    }
}