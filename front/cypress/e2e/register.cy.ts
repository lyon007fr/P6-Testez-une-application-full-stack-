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
