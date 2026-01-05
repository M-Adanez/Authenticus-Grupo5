package es.deusto.sd.proyecto.Service;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Entity.AnalysisType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class dataProcessingService {

    /**
     * Procesa el CaseInvestigation y devuelve un mapa:
     *
     *  - CONTENT_ALTERATION → lista de resultados
     *  - CONTENT_VERACITY → lista de resultados
     *
     * El tamaño de cada lista coincide con el número de imágenes.
     */
    public Map<AnalysisType, List<Float>> showCaseInvestigationResults(CaseInvestigation ci) {
        Map<AnalysisType, List<Float>> results = new HashMap<>();

        switch (ci.getType()) {
            case BOTH -> processBoth(ci, results);
            case CONTENT_ALTERATION -> processContentAlteration(ci, results);
            case CONTENT_VERACITY -> processContentVeracity(ci, results);
        }

        return results;
    }

    private void processBoth(CaseInvestigation ci, Map<AnalysisType, List<Float>> results) {
        int n = ci.getImageList().size();
        results.put(AnalysisType.CONTENT_ALTERATION, getRandomValues(n));
        results.put(AnalysisType.CONTENT_VERACITY, getRandomValues(n));
    }

    private void processContentAlteration(CaseInvestigation ci, Map<AnalysisType, List<Float>> results) {
        int n = ci.getImageList().size();
        results.put(AnalysisType.CONTENT_ALTERATION, getRandomValues(n));
        results.put(AnalysisType.CONTENT_VERACITY, Collections.nCopies(n, -1f));
    }

    private void processContentVeracity(CaseInvestigation ci, Map<AnalysisType, List<Float>> results) {
        int n = ci.getImageList().size();
        results.put(AnalysisType.CONTENT_VERACITY, getRandomValues(n));
        results.put(AnalysisType.CONTENT_ALTERATION, Collections.nCopies(n, -1f));
    }

    private List<Float> getRandomValues(int n) {
        Random ran = new Random();
        List<Float> values = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            values.add(ran.nextFloat());
        }

        return values;
    }
}
