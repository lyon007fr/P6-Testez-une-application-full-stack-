// cypress/support/commands.js
 /// <reference types="cypress" />

Cypress.Commands.add('login', (username, password) => {
    cy.visit('/login');
    cy.get('input[formControlName=email]').type(username);
    cy.get('input[formControlName=password]').type(password);
    cy.get('.mat-raised-button').click();
    cy.url().should('include', '/sessions');
  });

describe('Login spec', () => {
    beforeEach(() => {
      cy.visit('/login');
    });
  
    it('Login successfull', () => {
      
  
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true
        },
      })
  
      cy.intercept('GET', '/api/session', {
          statusCode: 200,
          body: [
            { id: 1, name: 'Yoga soir', date: "2023-01-01T00:00:00.000+00:00",teacher_id:1, description:"session nocturne",users:[], creadetAt:"2024-09-26T09:15:45", updatedAt:"2024-10-26T09:15:45" },
            { id: 2, name: 'Yoga midi', date: "2024-01-01T00:00:00.000+00:00",teacher_id:2, description:"session pause dej",users:[], creadetAt:"2024-09-26T09:15:45", updatedAt:"2024-10-26T09:15:45" },
            { id: 3, name: 'Yoga matin', date: "2025-01-01T00:00:00.000+00:00",teacher_id:3, description:"session matinal",users:[], creadetAt:"2024-09-26T09:15:45", updatedAt:"2024-10-26T09:15:45" }
          ],
        }).as('getSessions')
  
      cy.get('input[formControlName=email]').type("yoga@studio.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
  
      cy.url().should('include', '/sessions')
    })
  
    it('Login failed', () => {
    
  
      cy.intercept('POST', '/api/auth/login', {
        statusCode: 401,
        body: {
          message: 'Invalid credentials'
        },
      })
  
      cy.get('input[formControlName=email]').type("toto@hotmail{enter}{enter}")
      cy.get('p').should('contain.text', 'An error occurred')
      cy.url().should('include', '/login')
    })
  
    it('Login failed with empty fields', () => {
      
  
      cy.get('input[formControlName=email]').type("{enter}")
      cy.get('input[formControlName=password]').should('have.class', 'ng-invalid')
      cy.get("button").should('be.disabled')
      cy.url().should('include', '/login')
    });
  
  });

  describe('Register', () => {
    const user = [
      {
        id:1,  
        lastName: 'userName',
        firstName: 'UserFirtName',
        email: 'test@hotmail.com',
        password: 'test12345',
      }];
      beforeEach(() => {
          cy.visit('/register');
          }
  
          
      );
      it('should register', () => {
        
          cy.intercept('POST', '/api/auth/register', {
              statusCode: 200,
              body: user,
              }).as('register');
          
        cy.get('input[formControlName=firstName]').type(user[0].firstName);      
        cy.get('input[formControlName=lastName]').type(user[0].lastName);
        cy.get('input[formControlName=email]').type(user[0].email);
        cy.get('input[formControlName=password]').type(user[0].password);
          cy.get('.mat-raised-button').click();
          cy.url().should('include', '/login');
      });
  
      it('should not register if a field is empty', () => {
  
        cy.get('input[formControlName=firstName]').type(user[0].firstName);  
        
        cy.get('input[formControlName=email]').type(user[0].email);
        cy.get('input[formControlName=password]').type("test");
        cy.get('.mat-raised-button').should('be.disabled');
      
      });
  
      it('should not register if email is invalid', () => {
  
        cy.get('input[formControlName=firstName]').type(user[0].firstName);  
        cy.get('input[formControlName=lastName]').type(user[0].lastName);
        cy.get('input[formControlName=email]').type("test");
        cy.get('input[formControlName=password]').type(user[0].password);
        cy.get('.mat-raised-button').should('be.disabled');
  
        cy.get('input[formControlName=email]').should('have.class','ng-invalid');
      });
  
      it('should delete the user', () => {
  
        
          
        
            
  
          });
  
  });
  
 
  

/* Cypress.Commands.add('login', (username, password) => {
  cy.visit('/login');
  cy.get('input[formControlName=email]').type(username);
  cy.get('input[formControlName=password]').type(password);
  cy.get('.mat-raised-button').click();
  cy.url().should('include', '/sessions');
}); */

describe('Yoga Sessions Page', () => {
  const sessions = [
    {
      id: 1,
      name: 'Yoga matinal',
      description: 'matinal yoga',
      date: new Date(),
      teacher_id: 1,
      users: [],
      createdAt: new Date(),
      updatedAt: new Date(),
    },
    {
      id: 2,
      name: 'Yoga dejeuner',
      description: 'dejeuner yoga',
      date: new Date(),
      teacher_id: 2,
      users: [],
      createdAt: new Date(),
      updatedAt: new Date(),
    },
  ];

  const sessionsUpdated = [
    {
      id: 1,
      name: 'Yoga lever du soleil',
      description: 'yoga au aurore',
      date: new Date(),
      teacher_id: 1,
      users: [],
      createdAt: new Date(),
      updatedAt: new Date(),
    },
    {
      id: 2,
      name: 'Yoga dejeuner',
      description: 'dejeuner yoga',
      date: new Date(),
      teacher_id: 2,
      users: [],
      createdAt: new Date(),
      updatedAt: new Date(),
    },
  ];

  const teachers = [
    {
      id: 1,
      lastName: 'DELAHAYE',
      firstName: 'Margot',
      createdAt: new Date(),
      updatedAt: new Date(),
    },
    {
      id: 2,
      lastName: 'THIERCELIN',
      firstName: 'Hélène',
      createdAt: new Date(),
      updatedAt: new Date(),
    },
  ];

  const newSession = {
    id: 3,
    name: 'Nouveau yoga session',
    description: 'nouveau yoga session',
    date: new Date(),
    teacher_id: 2,
    users: [],
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const sessionAfterParticipate = {
    id: 1,
    name: 'Yoga matinal',
    description: 'matinal yoga',
    date: new Date(),
    teacher_id: 1,
    users: [1],
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  beforeEach(() => {
    if (Cypress.currentTest.title == 'should participate to a session') {

      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: false,
        },
      }).as('loginUserNotAdmin');

      cy.intercept('GET', '/api/teacher', {
        body: teachers,
      }).as('teachers');
  
      cy.intercept('GET', '/api/session', {
        statusCode: 200,
        body: sessions,
      }).as('getSessions');

      cy.login('user@studio.com', 'test!1234');
    }else{

    cy.intercept('GET', '/api/teacher', {
      body: teachers,
    }).as('teachers');

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: sessions,
    }).as('getSessions');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true,
      },
    }).as('login');

    cy.login('yoga@studio.com', 'test!1234');}
  });

  it('should display multiple yoga sessions', () => {
    cy.wait('@getSessions');
    cy.get('.mat-focus-indicator.item').should('have.length', 2);
    cy.get('.mat-focus-indicator.item').first().should('contain', 'Yoga matinal');
    cy.get('.mat-focus-indicator.item').last().should('contain', 'Yoga dejeuner');
  });

  it('should display a form to add a new yoga session', () => {
    cy.get('button').contains('Create').click();
    cy.get('.mat-form-field-appearance-outline').should('be.visible');
    cy.url().should('include', '/sessions/create');
  });

  it('should allow adding a new yoga session', () => {
    cy.intercept('POST', '/api/session', {
      statusCode: 201,
      body: newSession,
    }).as('newSession');

    cy.intercept('GET', '/api/session', { body: [...sessions, newSession] }).as('getSessionsAfterAdd');

    cy.get('button').contains('Create').click();
    cy.get('input[formcontrolname="name"]').type(newSession.name);
    cy.get('input[formcontrolname="date"]').type(newSession.date.toISOString().split('T')[0]);
    cy.get('textarea[formControlName=description]').type(newSession.description);
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('.mat-option-text').contains('Margot').click();
    cy.get('button[type="submit"]').click();

       
    cy.get('.mat-focus-indicator.item').should('have.length', 3);
    cy.get('.mat-focus-indicator.item').last().should('contain', newSession.name);
  });

  it('should allow updating a yoga session', () => {
    cy.intercept('PUT', `/api/session/${sessionsUpdated[0].id}`, {
      statusCode: 200,
      body: sessionsUpdated[0],
    }).as('sessionToUpdate');

    cy.intercept('GET', `/api/session/${sessionsUpdated[0].id}`, {
      body: sessionsUpdated[0],
    }).as('getSession');

    cy.intercept('GET', '/api/session', {
      body: sessionsUpdated,
    }).as('getUpdatedSessions');

    cy.get('.item').first().as('selectedSession');
    cy.get('mat-card-actions').find('button[mat-raised-button]').contains('Edit').click();
    cy.get('.mat-form-field-appearance-outline').should('be.visible');
    cy.get('input[formcontrolname="name"]').clear().type(sessionsUpdated[0].name);
    cy.get('input[formcontrolname="date"]').clear().type(sessionsUpdated[0].date.toISOString().split('T')[0]);
    cy.get('textarea[formControlName=description]').clear().type(sessionsUpdated[0].description);
    cy.get('button[type="submit"]').click();

    //cy.wait('@sessionToUpdate');
    cy.get('@selectedSession').within(() => {
      cy.contains(sessionsUpdated[0].name).should('exist');
      cy.contains(sessionsUpdated[0].description).should('exist');
    });
  });

  it('should allow deleting a yoga session', () => {

    
    cy.intercept('DELETE', `/api/session/${sessions[0].id}`, {
      statusCode: 204,
    }).as('sessionToDelete');

    cy.intercept('GET', `/api/session/${sessions[0].id}`, {
      statusCode: 200,
      body: sessions[0],
    }).as('getSessionToDelete');

     cy.intercept('GET', '/api/session', {
      body: [sessions[1]],
    }).as('getSessionsAfterDelete');

    cy.get('.item').first().as('selectedSession');
    cy.get('button').contains('Detail').click();
    cy.get('button').contains('Delete').click();

    
    cy.get('.mat-focus-indicator.item').should('have.length', 1);
    cy.get('.mat-focus-indicator.item').first().should('contain', 'Yoga dejeuner');
  });

  it('should not allow updating a yoga session if the form is invalid', () => {

    cy.intercept('GET', `/api/session/${sessions[0].id}`, {
      body: sessions[0],
    }).as('getSession');

    cy.get('.item').first().as('selectedSession');
    cy.get('mat-card-actions').find('button[mat-raised-button]').contains('Edit').click();
    cy.get('.mat-form-field-appearance-outline').should('be.visible');
    cy.get('input[formcontrolname="name"]').clear();
    cy.get('button[type="submit"]').should('be.disabled');
    
  });

  it('should participate to a session', () => {
    
    cy.intercept('GET', `/api/teacher/${sessions[0].teacher_id}`, {
      statusCode: 200,
      body: teachers,
    }).as('teachers');

    cy.intercept('POST', `/api/session/${sessions[0].id}/participate/1`, {
      statusCode: 200,
    }).as('participateSession');

    cy.intercept('GET', `/api/session/${sessions[0].id}`, {
      statusCode: 200,
      body: sessions[0],
    }).as('getSessionToParticipate');

    cy.get('.item').first().as('selectedSession');
    cy.get('button').contains('Detail').click();   
    cy.get('span').contains('0 attendee')
    
    cy.intercept('GET', `/api/session/${sessions[0].id}`, {
      statusCode: 200,
      body: sessionAfterParticipate,
    }).as('getSessionsAfterParticipate');

    cy.get('button').contains('Participate').click();
    cy.get('span').contains('1 attendee')
    cy.wait('@getSessionsAfterParticipate')
  });
});