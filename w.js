
    prog -> classDeclRepeat funcDefRepeat 'program' funcBody ';'
        classDeclRepeat -> classDecl classDeclRepeat | EPSILON
        funcDefRepeat -> funcDef funcDefRepeat | EPSILON
    classDecl -> 'class' 'id' classDeclOption '{' varDeclRepeat funcDeclRepeat '}' ';'
        classDeclOption -> ':' 'id' classDeclOptionRepeat | EPSILON
        classDeclOptionRepeat -> ',' 'id'
        varDeclRepeat -> varDecl varDeclRepeat | EPSILON
        funcDeclRepeat -> funcDecl funcDeclRepeat | EPSILON
funcDecl -> type 'id' '(' fParams ')' ';'

    funcHead -> type funcHeadOptionFactor '(' fParams ')'
    funcHeadOptionFactor -> 'id' funcHeadOption
        funcHeadOption -> 'sr' 'id' | EPSILON

funcDef -> funcHead funcBody ';'
    funcBody -> '{' varDeclRepeat statementRepeat '}'
        statementRepeat -> statement statementRepeat | EPSILON
    varDecl -> type 'id' arraySizeRepeat ';'
        arraySizeRepeat -> arraySize arraySizeRepeat | EPSILON
statement -> assignStat ';'
 | 'if' '(' expr ')' 'then' statBlock 'else' statBlock ';'
 | 'for' '(' type 'id' assignOp expr ';' relExpr ';' assignStat ')' statBlock ';'
 | 'get' '(' idnestRepeat variable ')' ';'
 | 'put' '(' expr ')' ';'
 | 'return' '(' expr ')' ';'


  assignStat -> idnestRepeat variable assignOp expr




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

            variableFunctionCallFactor -> 'id' idnest idnestRepeatFactor
                    idnestRepeatFactor ->  '.' 'id' idnest idnestRepeatFactor  | EPSILON

                    indiceRepeat -> indice indiceRepeat | EPSILON
            idnest -> variable | functionCall
            variable ->  indiceRepeat
            functionCall ->  '(' aParams ')'
                    idnestRepeat -> 'id' idnest '.' idnestRepeat |EPSILON






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
