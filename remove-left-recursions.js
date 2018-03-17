
    prog -> classDeclRepeat funcDefRepeat 'program' funcBody ';'
        classDeclRepeat -> classDecl classDeclRepeat | EPSILON
        funcDefRepeat -> funcDef funcDefRepeat | EPSILON

classDecl -> 'class' 'id' classDeclOption '{' varDeclRepeatfuncDeclRepeat '}' ';'

        classDeclOption -> ':' 'id' classDeclOptionRepeat | EPSILON
        classDeclOptionRepeat -> ',' 'id'

        funcDeclRepeat -> funcDecl funcDeclRepeat | EPSILON
funcDecl -> type 'id' '(' fParams ')' ';'

    funcHead -> type funcHeadOptionFactor '(' fParams ')'
    funcHeadOptionFactor -> 'id' funcHeadOption
        funcHeadOption -> 'sr' 'id' | EPSILON



funcDef -> funcHead funcBody ';'
    funcBody -> '{' varDeclRepeat statementRepeat '}'
        statementRepeat -> statement statementRepeat | EPSILON
classDecl -> 'class' 'id' classDeclOption '{' varDeclRepeatfuncDeclRepeat '}' ';'
      varDeclRepeatfuncDeclRepeat -> varDeclRepeat varDeclRepeatfuncDeclRepeat2 
      varDeclRepeatfuncDeclRepeat2 -> funcDeclRepeat

        varDeclRepeat -> varDecl varDeclRepeat | EPSILON
    varDecl -> type 'id' arraySizeRepeat ';'
        arraySizeRepeat -> arraySize arraySizeRepeat | EPSILON
statement -> assignStat ';'
 | 'if' '(' expr ')' 'then' statBlock 'else' statBlock ';'
 | 'for' '(' type 'id' assignOp expr ';' relExpr ';' assignStat ')' statBlock ';'

   | 'get' '(' idnestRepeatFactor variable ')' ';'

 | 'put' '(' expr ')' ';'
 | 'return' '(' expr ')' ';'


  assignStat -> idnestRepeatFactor variable assignOp expr




statBlock -> '{' statementRepeat '}' | statement | EPSILON

            expr -> relExpr
            relExpr -> arithExpr relExprMod
                relExprMod ->  relOp arithExpr relExprMod| EPSILON
            arithExpr -> term arithExprMod
                arithExprMod -> addOp term arithExpr | EPSILON

sign -> '+' | '-'
        term -> factor termMod
            termMod -> multOp factor termMod | EPSILON


            factor -> variableFunctionCallFactor
             | 'intNum' | 'floatNum'
             | '(' arithExpr ')'
             | 'not' factor
             | sign factor

            variableFunctionCallFactor -> idnestRepeatFactor  variableFunctionCallFactorMod
                    idnestFactor -> 'id' idnestRepeat | EPSILON

                    idnestRepeat -> idnest idnestRepeat | EPSILON
                    indiceRepeat -> indice indiceRepeat | EPSILON
            variableFunctionCallFactorMod -> variable | functionCall
            variable ->  indiceRepeat
            functionCall ->  '(' aParams ')'


    idnest -> 'id' idnestMod
    idnestMod ->  indiceRepeat '.'
     | '(' aParams ')' '.'


indice -> '[' arithExpr ']'
arraySize -> '[' 'intNum' ']'
type -> 'int' | 'float' | 'id'
    fParams -> type 'id' arraySizeRepeat fParamsTailRepeat | EPSILON
        fParamsTailRepeat -> fParamsTail fParamsTailRepeat | EPSILON
    aParams -> expr aParamsTailRepeat | EPSILON
        aParamsTailRepeat -> aParamsTail aParamsTailRepeat | EPSILON
fParamsTail -> ',' type 'id' arraySizeRepeat
aParamsTail -> ',' expr
assignOp -> '='
relOp -> 'eq' | 'neq' | 'lt' | 'gt' | 'leq' | 'geq'
addOp -> '+' | '-' | 'or'
multOp -> '*' | '/' | 'and'
