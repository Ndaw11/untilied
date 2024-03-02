package sn.dev.service;

import sn.dev.entity.Etudiant;

public interface IEtudiant {
    Etudiant saisir();
    void afficher(Etudiant etudiant);
}
