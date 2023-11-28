import { FormControl, ValidationErrors } from "@angular/forms";

export class CustomValidator {
    //white space validation
    static notOnlyWhiteSpace(control: FormControl): ValidationErrors{
        //check if string only contains whitespace
        if((control.value != null) && (control.value.trim().length === 0)){
            //invalid, return the error object
            return {'notOnlyWhiteSpace': true};
        }
        else{
            //valid, return null
            return {'notOnlyWhiteSpace': null};
        }
    }
}
