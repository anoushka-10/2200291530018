package com.Test.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AverageResponse {
	  private List<Integer> windowPrevState;
	    private List<Integer> windowCurrState;
	    private List<Integer> numbers;
	    private double avg;
		
}
