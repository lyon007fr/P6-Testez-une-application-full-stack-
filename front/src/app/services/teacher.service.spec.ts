import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { TeacherService } from './teacher.service';
import { Teacher } from '../interfaces/teacher.interface';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],  // Utiliser HttpClientTestingModule
      providers: [TeacherService]
    });
    service = TestBed.inject(TeacherService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();  // Vérification des requêtes HTTP en attente
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return a list of teachers', () => {
    let teachers: Teacher[] | undefined;

    service.all().subscribe((data) => {
      teachers = data;
    });

    const req = httpTestingController.expectOne('api/teacher');
    req.flush([{ id: 1, lastName: "teacher1",firstname:"toto", createdAt: "2024-09-26T08:32:19", updatedAt:"2024-09-26T08:32:19"}]); // API response

    expect(teachers).toEqual([{ id: 1, lastName: "teacher1",firstname:"toto", createdAt: "2024-09-26T08:32:19", updatedAt:"2024-09-26T08:32:19"}]);  // Vérification de la valeur de retour
    expect(req.request.method).toBe('GET');  // Vérification que la méthode HTTP est bien GET
  });

  it('should return a teacher', () => {
    let teacher: Teacher | undefined;

    service.detail('2').subscribe((data) => {
      teacher = data;
    });

    const req = httpTestingController.expectOne('api/teacher/2');
    req.flush({ id: 2, lastName: "tata",firstname:"toto", createdAt: "2024-09-26T08:32:19", updatedAt:"2024-09-26T08:32:19"});  // API response

    expect(teacher).toEqual({ id: 2, lastName: "tata",firstname:"toto", createdAt: "2024-09-26T08:32:19", updatedAt:"2024-09-26T08:32:19"});  // Vérification de la valeur de retour
    expect(req.request.method).toBe('GET');  // Vérification que la méthode HTTP est bien GET
  }
)}
);
