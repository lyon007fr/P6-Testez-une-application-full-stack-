
/// <reference types="cypress" />
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