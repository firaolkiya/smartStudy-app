package com.example.smartstudy.Presentaion.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartstudy.Domain.Model.Subjects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetSelecter(
    isOpen:Boolean,
    subjects:List<Subjects>,
    bottomSheetTitle:String,
    sheetState:SheetState,
    onDismissRequest:()->Unit,
    onSubjectCLicked:(Subjects)->Unit
){
    if(isOpen){
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                        BottomSheetDefaults.DragHandle()
                        Text(text = bottomSheetTitle)
                        Spacer(modifier = Modifier.height(10.dp)
                        )
                        Divider()

                }
            }
        ) {
            LazyColumn {
                items(subjects){subject->
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSubjectCLicked(subject) }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = subject.name)
                    }
                    if (subjects.isEmpty()){
                        Text(text = "No Subject to Show")
                    }
                }
            }
            
        }
        
    }
    
    
}