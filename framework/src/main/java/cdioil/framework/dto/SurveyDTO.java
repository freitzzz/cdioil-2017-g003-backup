package cdioil.framework.dto;

public class SurveyDTO extends GenericDTO implements DTO {

    public SurveyDTO(String type, String name, String endDate,
                     String state) {
        super(type);
        setName(name);

        if (endDate.startsWith("+")) {
            endDate = "Indefinido";
        }

        setEndDate(endDate);

        setState(state);
    }

    public void setName(String name) {
        super.put("name", name);
    }

    public void setEndDate(String endDate) {
        super.put("endDate", endDate);
    }

    public void setState(String state) {
        super.put("state", state);
    }

    public String getName() {
        return super.get("name").toString();
    }

    public String getEndDate() {
        return super.get("endDate").toString();
    }

    public String getState() {
        return super.get("state").toString();
    }
}
