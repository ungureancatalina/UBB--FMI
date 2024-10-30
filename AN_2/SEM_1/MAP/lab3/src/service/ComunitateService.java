package service;

import domain.Utilizator;
import domain.Prietenie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComunitateService {
    UtilizatorService utilizatorService;
    HashMap<Long, List<Long>> lista_adiacenta;

    public ComunitateService(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

    void DFS(Long v, HashMap<Long, Boolean> visited) {
        visited.put(v, true);
        System.out.print(v + ", " + this.utilizatorService.findUtilizator(v).getFirstName() + " " + this.utilizatorService.findUtilizator(v).getLastName());
        if (lista_adiacenta.containsKey(v))
            for (Long adj : lista_adiacenta.get(v))
                if (!visited.containsKey(adj))
                    DFS(adj, visited);
    }

    public int connectComunitate() {
        lista_adiacenta = new HashMap<Long, List<Long>>();
        for (Utilizator utilizator : utilizatorService.getUtilizator()) {
            List<Long> prieteni = new ArrayList<>();
            for (Prietenie prieten : utilizatorService.getPrietenii()) {
                if (prieten.getIdUser1().equals(utilizator.getId()))
                    prieteni.add(prieten.getIdUser2());
                if (prieten.getIdUser2().equals(utilizator.getId()))
                    prieteni.add(prieten.getIdUser1());
            }
            if (!prieteni.isEmpty())
                this.lista_adiacenta.put(utilizator.getId(), prieteni);
        }
        List<Long> ids = new ArrayList<>();
        for (Utilizator utilizator : utilizatorService.getUtilizator())
            ids.add(utilizator.getId());

        int nr_comunitati = 0;
        HashMap<Long, Boolean> visited = new HashMap<Long, Boolean>();
        for (Long id : ids)
            if (!visited.containsKey(id)) {
                DFS(id, visited);
                nr_comunitati++;
                System.out.println();
            }
        return nr_comunitati;
    }

    public List<Long> comunitateSociabila() {
        lista_adiacenta = new HashMap<Long, List<Long>>();
        List<Long> maxim = new ArrayList<>();
        for (Utilizator utilizator : utilizatorService.getUtilizator()) {
            List<Long> prieteni = new ArrayList<>();
            for (Prietenie prieten : utilizatorService.getPrietenii()) {
                if (prieten.getIdUser1().equals(utilizator.getId()))
                    prieteni.add(prieten.getIdUser2());
                if (prieten.getIdUser2().equals(utilizator.getId()))
                    prieteni.add(prieten.getIdUser1());
            }
            if (!prieteni.isEmpty()) {
                this.lista_adiacenta.put(utilizator.getId(), prieteni);
                if (maxim.size() < prieteni.size() + 1) {
                    maxim = prieteni;
                    maxim.add(utilizator.getId());
                }
            }
        }
        return maxim;
    }
}
