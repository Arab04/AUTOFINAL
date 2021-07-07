package com.auto.service.entity.enums;

public enum AvtoServiceStatusEnum {

    NO_STATEMENT(0, "NO STATEMENT"),
    HADAVOY(1, "HADAVOY"),
    MOTORIST(2, "MOTORIST"),
    VULKANIZATSIYA(3, "VULKANIZATSIYA"),
    RADIATOR(4, "RADIATOR"),
    KUZOVCHI(5, "KUZOVCHI"),
    ELEKTRIK(6, "ELEKTRIK"),
    ALL(7, "ALL");

    private int value;
    private String strValue;

    AvtoServiceStatusEnum(int value, String strValue)
    {
        this.value = value;
        this.strValue = strValue;
    }

    public int getValue()
    {
        return value;
    }

    public String getStrValue()
    {
        return strValue;
    }

    /**
     * Returns the enumeration value that has the same 'strValue' with the given 'inpStrVal'
     *
     * @param inpStrVal
     * @return
     */
    public static AvtoServiceStatusEnum getFromStringValue(String inpStrVal)
    {
        if (inpStrVal == null)
        {
            return NO_STATEMENT;
        }
        else
        {
            if (inpStrVal.compareToIgnoreCase(HADAVOY.strValue) == 0)
            {
                return HADAVOY;
            }
            else if (inpStrVal.compareToIgnoreCase(MOTORIST.strValue) == 0)
            {
                return MOTORIST;
            }
            else if (inpStrVal.compareToIgnoreCase(VULKANIZATSIYA.strValue) == 0)
            {
                return VULKANIZATSIYA;
            }
            else if (inpStrVal.compareToIgnoreCase(RADIATOR.strValue) == 0)
            {
                return RADIATOR;
            }
            else if (inpStrVal.compareToIgnoreCase(KUZOVCHI.strValue) == 0)
            {
                return KUZOVCHI;
            }
            else if (inpStrVal.compareToIgnoreCase(ELEKTRIK.strValue) == 0)
            {
                return ELEKTRIK;
            }
            else if (inpStrVal.compareToIgnoreCase(ALL.strValue) == 0)
            {
                return ALL;
            }
            else
            {
                return NO_STATEMENT;
            }
        }
    }

}
