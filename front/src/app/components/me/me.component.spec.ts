import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { SessionService } from '../../services/session.service';
import { User } from '../../interfaces/user.interface';
import { UserService } from '../../services/user.service';
import { MeComponent } from './me.component';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userService: UserService;
  let sessionService: SessionService;
  let matSnackBar: MatSnackBar;
  let router: Router;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 123
    },
    logOut: jest.fn() // Mock de la méthode logOut
  };

  beforeEach(async () => {
    const mockUserService = {
      delete: jest.fn().mockReturnValue(of(null)), // Mock de la méthode delete,
      getById: jest.fn().mockReturnValue(of({} as User)) // Mock de la méthode getById, elle est utilisée dans le ngOnInit
    };

    const mockRouter = {
      navigate: jest.fn() // Mock de la méthode navigate
    };

    const mockMatSnackBar = {
      open: jest.fn() // Mock de la méthode open
    };

    await TestBed.configureTestingModule({
      declarations: [MeComponent], // Déclaration du composant à tester
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService }, // Mock de SessionService
        { provide: UserService, useValue: mockUserService }, // Mock de UserService
        { provide: MatSnackBar, useValue: mockMatSnackBar}, // Mock de MatSnackBar
        { provide: Router, useValue: mockRouter } // Mock de Router
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent); // Création du composant
    component = fixture.componentInstance; // Récupération de l'instance du composant

    userService = TestBed.inject(UserService); // Récupération du service UserService
    sessionService = TestBed.inject(SessionService); // Récupération du service SessionService
    matSnackBar = TestBed.inject(MatSnackBar); // Récupération du service MatSnackBar
    router = TestBed.inject(Router); // Récupération du service Router

    fixture.detectChanges(); // Détection des changements
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call back', () => {
    const spy = jest.spyOn(component, 'back'); // Création d'un spy sur la méthode back
    component.back(); // Appel de la méthode back
    expect(component.back).toHaveBeenCalled(); // Vérification que la méthode back a bien été appelée
  });

  it('should call delete and perform expected actions', () => {
    // Mock de la méthode delete pour simuler une réponse réussie
    jest.spyOn(userService, 'delete').mockReturnValue(of(null)); 
    const snackBarSpy = jest.spyOn(matSnackBar, 'open'); // Spy sur la méthode open du MatSnackBar
    const routerSpy = jest.spyOn(router, 'navigate'); // Spy sur la méthode navigate du Router

    component.delete(); // Appel de la méthode delete

    // Vérification que la méthode delete de userService a bien été appelée avec le bon ID
    expect(userService.delete).toHaveBeenCalledWith('123');

    // Vérification que la méthode open du MatSnackBar a bien été appelée avec les bons arguments
    expect(snackBarSpy).toHaveBeenCalledWith('Your account has been deleted !', 'Close', { duration: 3000 });

    // Vérification que la méthode logOut de sessionService a bien été appelée
    expect(sessionService.logOut).toHaveBeenCalled();

    // Vérification que la redirection vers la page '/' a bien eu lieu
    expect(routerSpy).toHaveBeenCalledWith(['/']);
  });
});
