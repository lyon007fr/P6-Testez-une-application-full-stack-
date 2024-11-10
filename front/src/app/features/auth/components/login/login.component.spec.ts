import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { SessionService } from '../../../../services/session.service';
import { LoginComponent } from './login.component';
import { SessionInformation } from '../../../../interfaces/sessionInformation.interface';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let sessionService: SessionService;
  let router: Router;

  // Mock des services utilisés
  const mockAuthService = {
    login: jest.fn()
  };

  const mockSessionService = {
    logIn: jest.fn()
  };

  const mockRouter = {
    navigate: jest.fn()
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent], // Déclaration du composant à tester
      imports: [ReactiveFormsModule],  // Import des modules nécessaires, ici ReactiveFormsModule pour le formulaire
      providers: [
        { provide: AuthService, useValue: mockAuthService },  // Injection du service AuthService mocké
        { provide: SessionService, useValue: mockSessionService },  // Injection du service SessionService mocké
        { provide: Router, useValue: mockRouter }  // Injection du Router mocké
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);  // Création de l'instance du composant
    component = fixture.componentInstance;  // Récupération de l'instance du composant
    authService = TestBed.inject(AuthService);  // Récupération de l'instance du service AuthService
    sessionService = TestBed.inject(SessionService);  // Récupération de l'instance du service SessionService
    router = TestBed.inject(Router);  // Récupération de l'instance du service Router

    fixture.detectChanges();  // Déclenchement de la détection des changements
  });

  it('should create', () => {
    // Vérifie que le composant est bien créé
    expect(component).toBeTruthy();
  });

  it('should call authService.login and sessionService.logIn on successful submit', () => {
    // Mock d'une réponse de connexion réussie
    const mockSessionInfo: SessionInformation = {
      token: 'test-token',
      type: 'Bearer',
      id: 1,
      username: 'testUser',
      firstName: 'John',
      lastName: 'Doe',
      admin: false
    };
    mockAuthService.login.mockReturnValue(of(mockSessionInfo));  // Simule un succès lors de l'appel à authService.login()

    // Définit les valeurs du formulaire
    component.form.setValue({ email: 'test@example.com', password: 'password123' });

    // Appel de la méthode submit
    component.submit();

    // Vérifie que authService.login() a été appelé avec les bons arguments
    expect(authService.login).toHaveBeenCalledWith({
      email: 'test@example.com',
      password: 'password123'
    });

    // Vérifie que sessionService.logIn() a bien été appelé avec la réponse du login
    expect(sessionService.logIn).toHaveBeenCalledWith(mockSessionInfo);

    // Vérifie que la navigation vers '/sessions' a bien été déclenchée
    expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('should set onError to true if login fails', () => {
    // Mock d'une réponse de connexion échouée
    mockAuthService.login.mockReturnValue(throwError(() => new Error('Invalid credentials')));

    // Définit les valeurs du formulaire
    component.form.setValue({ email: 'test@example.com', password: 'wrongPassword' });

    // Appel de la méthode submit
    component.submit();

    // Vérifie que la variable onError est passée à true en cas d'erreur
    expect(component.onError).toBe(true);
  });
});
