package es.deusto.sd.proyecto.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.sd.proyecto.Entity.AnalysisType;
import es.deusto.sd.proyecto.Entity.CaseInvestigation;

public class CaseInvestigationDTO {

    private long userId;
    private String name;
    private AnalysisType type;
    private Date date;
    private List<String> imageList;
    private Map<AnalysisType, Float> results;

    public CaseInvestigationDTO() {}

    public CaseInvestigationDTO(String name, AnalysisType type, Date date, List<String> imageList) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.imageList = imageList;
    }

        public CaseInvestigationDTO(String name, AnalysisType type, Date date, List<String> imageList, Map<AnalysisType, Float> results) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.imageList = imageList;
        this.results = results;
    }

    public CaseInvestigationDTO(CaseInvestigation ci) {
        this.name = ci.getName();
        this.type = ci.getType();
        this.date = ci.getDate();
        this.imageList = ci.getImageList() != null ? new ArrayList<>(ci.getImageList()) : List.of();
        this.results = ci.getResults() != null ? new HashMap<>(ci.getResults()) : Map.of();

    }


    public String getName() { return name; }
    public AnalysisType getType() { return type; }
    public Date getDate() { return date; }
    public List<String> getImageList() { return imageList; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Map<AnalysisType, Float> getResults(){ return this.results;}
}
