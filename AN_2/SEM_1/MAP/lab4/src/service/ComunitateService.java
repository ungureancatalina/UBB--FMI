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
        if (lista_adiacenta.containsKey(v)) {
            lista_adiacenta.get(v).stream()
                    .filter(x -> !visited.getOrDefault(x, false))
                    .forEach(x -> DFS(x, visited));
        }
    }

    public int connectComunitate() {
        lista_adiacenta = new HashMap<>();

        utilizatorService.getUtilizator().forEach(utilizator -> {
            List<Long> prieteni = new ArrayList<>();
            utilizatorService.getPrietenii().forEach(prieten -> {
                if (prieten.getIdUser1().equals(utilizator.getId())) {
                    prieteni.add(prieten.getIdUser2());
                }
                if (prieten.getIdUser2().equals(utilizator.getId())) {
                    prieteni.add(prieten.getIdUser1());
                }
            });
            if (!prieteni.isEmpty()) {
                lista_adiacenta.put(utilizator.getId(), prieteni);
            }
        });

        List<Long> ids = new ArrayList<>();
        utilizatorService.getUtilizator().forEach(utilizator -> ids.add(utilizator.getId()));

        final int[] nr_comunitati = {0};
        HashMap<Long, Boolean> visited = new HashMap<>();
        ids.forEach(id -> {
            if (!visited.getOrDefault(id, false)) {
                DFS(id, visited);
                nr_comunitati[0]++;
                System.out.println();
            }
        });

        return nr_comunitati[0];
    }

    public List<Long> comunitateSociabila() {
        lista_adiacenta = new HashMap<>();
        List<Long>[] maxim = new List[]{new ArrayList<>()};

        utilizatorService.getUtilizator().forEach(utilizator -> {
            List<Long> prieteni = new ArrayList<>();

            utilizatorService.getPrietenii().forEach(prieten -> {
                if (prieten.getIdUser1().equals(utilizator.getId())) {
                    prieteni.add(prieten.getIdUser2());
                }
                if (prieten.getIdUser2().equals(utilizator.getId())) {
                    prieteni.add(prieten.getIdUser1());
                }
            });

            if (!prieteni.isEmpty()) {
                this.lista_adiacenta.put(utilizator.getId(), prieteni);

                if (maxim[0].size() < prieteni.size() + 1) {
                    maxim[0] = new ArrayList<>(prieteni);
                    maxim[0].add(utilizator.getId());
                }
            }
        });

        return maxim[0];
    }
}
