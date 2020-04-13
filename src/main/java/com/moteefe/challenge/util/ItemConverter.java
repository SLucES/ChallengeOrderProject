package com.moteefe.challenge.util;

import com.moteefe.challenge.domain.Item;
import com.moteefe.challenge.dto.ItemDto;

public class ItemConverter {

	public static Item convertToEntity(ItemDto itemDto) {
		Item item = new Item();
		
		item.setCount(itemDto.getCount());
		item.setTitle(itemDto.getTitle());;
		
		return item;
	}
	
	public static ItemDto convertToDto(Item item) {
		ItemDto itemDto = new ItemDto();
		
		itemDto.setCount(item.getCount());
		itemDto.setTitle(item.getTitle());;
		
		return itemDto;
	}
	
}
