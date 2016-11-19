package avisha.com.myorganizer.model;

public class DataModal {
    private String mLabel;
    private String mDescription;

    public DataModal(String label, String desc) {
        mLabel = label;
        mDescription = desc;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}