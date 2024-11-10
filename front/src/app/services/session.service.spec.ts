import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { take } from 'rxjs/operators'; 
import { SessionInformation } from '../interfaces/sessionInformation.interface'; 
import { SessionService } from './session.service';
import { ObserverSpy } from '@hirez_io/observer-spy'; // Import de subscribeSpyTo

describe('SessionService', () => {
  let service: SessionService;
  let testUser: SessionInformation = {
    token: 'test-token',
    type: 'user',
    id: 1,
    username: 'testUser',
    firstName: 'Test',
    lastName: 'User',
    admin: false
  };

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should emit the logged in status as an Observable boolean', () => {
    // Utilisation de subscribeSpyTo pour observer les valeurs émises par l'Observable
    const observerSpy = new ObserverSpy<boolean>(); //création d'un observer qui va observer les valeurs émises par l'observable
    service.$isLogged().subscribe(observerSpy); //observerSpy va observer les valeurs émises par l'observable $isLogged

    // Vérifier que le service émet d'abord false (non connecté)
    expect(observerSpy.getLastValue()).toBe(false);

    // Simuler la connexion d'un utilisateur
    service.logIn(testUser);
    expect(observerSpy.getLastValue()).toBe(true);
    expect(service.isLogged).toBe(true);

    // Simuler la déconnexion de l'utilisateur
    service.logOut();
    expect(observerSpy.getLastValue()).toBe(false);
    expect(service.isLogged).toBe(false);
  });

  describe('login and logout', () => {
    it('should set login user', () => {
      service.logIn(testUser);
      expect(service.isLogged).toBe(true);
      expect(service.sessionInformation).toEqual(testUser);
    });

    it('should set logout user', () => {
      service.logOut();
      expect(service.isLogged).toBe(false);
      expect(service.sessionInformation).toBeUndefined();
    });
  });
});
