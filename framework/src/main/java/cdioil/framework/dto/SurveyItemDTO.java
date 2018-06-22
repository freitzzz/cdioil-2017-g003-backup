package cdioil.framework.dto;

public class SurveyItemDTO extends GenericDTO implements DTO {

    public SurveyItemDTO(String type, String name, String itemID) {
        super(type);

        setName(name);
        setItemID(itemID);
    }

    public void setName(String name) {
        super.put("name", name);
    }

    public String getName() {
        return super.get("name").toString();
    }

    public void setItemID(String itemID) {
        super.put("itemID", itemID);
    }

    public String getItemID() {
        return super.get("itemID").toString();
    }

    public String getType() {
        return type();
    }

    @Override
    public String toString() {
        return getName();
    }
}
