package es.deusto.sd.proyecto.Entity;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "case_investigation")
public class CaseInvestigation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AnalysisType type;

    private Date date;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> imageList = new ArrayList<>();

    private Long userId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "case_results")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<AnalysisType, Float> results = new HashMap<>();

    public CaseInvestigation() {}

    public CaseInvestigation(String name, AnalysisType type, Date date, List<String> imageList) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.imageList = imageList;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public AnalysisType getType() { return type; }
    public Date getDate() { return date; }
    public List<String> getImageList() { return imageList; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Map<AnalysisType, Float> getResults() { return results; }
    public void setResults(Map<AnalysisType, Float> results) { this.results = results; }
}
